package com.blazer.mq.client;

import com.blazer.mq.api.Client;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class ClientModule extends AbstractModule {
    public static final String TCP = "tcp://0.0.0.0:61616";
    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(TCP);


    @Override
    protected void configure() {
        bind(EventBus.class).in(Singleton.class);
        bind(Client.class).to(QueueClient.class);
        bind(QueueNotificator.class);
        bind(ServiceProxy.class);
    }

    @Provides
    @Singleton
    Connection provideConnection() throws JMSException {
        return connectionFactory.createConnection();
    }

    @Inject
    @Provides
    Session provideSession(Connection connection) throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
