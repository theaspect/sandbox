package com.blazer.mq.client;

import com.blazer.mq.api.Client;
import com.blazer.mq.api.service.Events;
import com.blazer.mq.api.service.FooService;
import com.blazer.mq.api.service.OtherException;
import com.blazer.mq.api.service.SomeException;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.JMSException;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class ClientApp {
    public static void main(String[] args) throws JMSException, InterruptedException {
        Injector injector = Guice.createInjector(new ClientModule());
        Connection connection = injector.getInstance(Connection.class);
        connection.start();

        EventBus eventBus = injector.getInstance(EventBus.class);
        eventBus.register(new ClientApp());

        Client client = injector.getInstance(Client.class);
        QueueNotificator notificator = injector.getInstance(QueueNotificator.class);

        ServiceProxy serviceProxy = injector.getInstance(ServiceProxy.class);

        FooService fooService = serviceProxy.getService(FooService.class);
        ClientApp.log.debug(fooService.get());
        Thread.sleep(1000);
        ClientApp.log.debug(fooService.echo("Some message"));
        Thread.sleep(1000);
        try {
            fooService.send(null, 0);
        } catch (SomeException | OtherException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        try {
            fooService.send("", null);
        } catch (SomeException | OtherException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        try {
            fooService.send("", 0);
        } catch (SomeException | OtherException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onSomeEvent(Events.SomeEvent e) {
        log.debug(e.getMessage());
    }
}
