package com.blazer.mq.server;

import com.blazer.mq.api.Request;
import com.blazer.mq.api.Response;
import com.blazer.mq.api.Server;
import com.blazer.mq.server.service.AbstractService;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Slf4j
public class QueueServer implements Server {
    private Session session;
    private MessageConsumer request;
    private MessageProducer response;

    @Inject
    public QueueServer(Session session) throws JMSException {
        this.session = session;

        request = session.createConsumer(session.createTopic(RMI));
        response = session.createProducer(null); // Destination will be gotten from JMSReplyTo

        request.setMessageListener(new RequestListener());
    }

    @Override
    public Response receiveMessage(Request message) {
        try {
            return new Response(message.getGuid(), (Serializable) invoke(message));
        } catch (Exception e) {
            log.debug("Exception", e);
            return new Response(message.getGuid(), e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void sendResponse(Destination jmsReplyTo, Serializable message) {
        QueueServer.log.info("Sending response " + message);
        try {
            response.send(jmsReplyTo, session.createObjectMessage(message));
        } catch (JMSException e) {
            log.error("Cannot send response {}", e);
        }
    }

    class RequestListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            try {
                ObjectMessage om = (ObjectMessage) message;
                sendResponse(om.getJMSReplyTo(), receiveMessage((Request) (om.getObject())));
            } catch (JMSException e) {
                log.error("Invocation error", e);
            }
        }
    }

    public Object invoke(Request invocation) throws Exception {
        @SuppressWarnings("unchecked")
        Class<? extends AbstractService> clazz = (Class<? extends AbstractService>)
                Class.forName(AbstractService.PREFIX + invocation.getService());

        Method method = null;
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(invocation.getMethod()) && m.getParameterTypes().length == invocation.getArgs().length) {
                method = m;
                break;
            }
        }

        if (method != null) {
            try {
                return method.invoke(clazz.newInstance(), invocation.getArgs());
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                } else {
                    throw e;
                }
            }
        } else {
            throw new IllegalAccessError(invocation.getService() + ":" + invocation.getMethod() + "(" + Arrays.toString(invocation.getArgs()) + ")");
        }
    }
}
