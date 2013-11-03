package com.blazer.mq.server;

import com.blazer.mq.api.Notificator;
import com.blazer.mq.api.Server;
import com.google.inject.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class ServerModule extends AbstractModule {
    public static final String VM = "vm://localhost";
    public static final String TCP = "tcp://0.0.0.0:61616";

    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(VM);


    @Override
    protected void configure() {
        bind(Server.class).to(QueueServer.class);
        bind(Notificator.class).to(QueueNotificator.class);
    }

    @Provides
    @Singleton
    BrokerService provideBroker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("localhost");

        //Add a network connection
        broker.addConnector(VM);
        broker.addConnector(TCP);
        broker.setDataDirectory("target/db");
        broker.deleteAllMessages(); // Cleanup

        return broker;
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
