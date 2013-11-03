package com.blazer.mq.server;

import com.blazer.mq.api.Notificator;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.io.Serializable;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Slf4j
public class QueueNotificator implements Notificator {
    private Session session;
    private MessageProducer notification;

    @Inject
    public QueueNotificator(Session session) throws JMSException {
        this.session = session;

        notification = session.createProducer(session.createTopic(EVENTS));
    }

    @Override
    public void sendNotification(Serializable event) {
        QueueNotificator.log.info("Sending notification " + event);
        try {
            notification.send(session.createObjectMessage(event));
        } catch (JMSException e) {
            QueueNotificator.log.error("Cannot send notification", e);
        }
    }
}
