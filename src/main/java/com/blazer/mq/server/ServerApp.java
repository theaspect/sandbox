package com.blazer.mq.server;

import com.blazer.mq.api.Notificator;
import com.blazer.mq.api.Server;
import com.blazer.mq.api.service.Events;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.activemq.broker.BrokerService;

import javax.jms.Connection;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class ServerApp {
    private static final Injector injector = Guice.createInjector(new ServerModule());

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = injector.getInstance(BrokerService.class);
        brokerService.start();

        Connection connection = injector.getInstance(Connection.class);
        connection.start();

        final Server server = injector.getInstance(Server.class);
        final Notificator notificator = injector.getInstance(Notificator.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    notificator.sendNotification(new Events.SomeEvent("Some " + i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
