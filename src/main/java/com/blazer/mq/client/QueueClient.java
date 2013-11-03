package com.blazer.mq.client;

import com.blazer.mq.api.Client;
import com.blazer.mq.api.Request;
import com.blazer.mq.api.Response;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class QueueClient implements Client {
    private Session session;
    private Destination temporaryQueue;
    private MessageProducer request;
    private MessageConsumer response;


    @Inject
    public QueueClient(final Session session) throws JMSException {
        this.session = session;

        temporaryQueue = session.createTemporaryQueue();
        request = session.createProducer(session.createTopic(RMI));
        response = session.createConsumer(temporaryQueue);
    }

    @Override
    public void sendRequest(Request message) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(message);
        objectMessage.setJMSReplyTo(temporaryQueue);
        request.send(objectMessage);
    }

    @Override
    public Response getResponse() throws JMSException {
        ObjectMessage message = (ObjectMessage) response.receive(10000l);
        if (message == null) {
            throw new RuntimeException("Response timeout");
        }
        return (Response) message.getObject();
    }
}
