package com.blazer.mq.client;

import com.blazer.mq.api.Notificator;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Slf4j
public class QueueNotificator {
    private ConcurrentLinkedQueue<Object> notificationsQueue = new ConcurrentLinkedQueue<Object>();
    private EventBus eventBus;
    private MessageConsumer notification;

    private Runnable notificationReader = new Runnable() {
        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run() {
            while (true) {
                while (notificationsQueue.size() > 0) {
                    Object event = notificationsQueue.poll();
                    QueueNotificator.log.debug("Submit new event {}", event);
                    eventBus.post(event);
                }
            }
        }
    };

    @Inject
    public QueueNotificator(final EventBus eventBus, final Session session) throws JMSException {
        this.eventBus = eventBus;

        notification = session.createConsumer(session.createTopic(Notificator.EVENTS));
        notification.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    Object msg = ((ObjectMessage) message).getObject();

                    notificationsQueue.add(msg);
                } catch (Exception e) {
                    QueueNotificator.log.error("Notification error", e);
                }
            }
        });

        new Thread(notificationReader).start();
    }
}
