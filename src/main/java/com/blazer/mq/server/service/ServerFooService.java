package com.blazer.mq.server.service;

import com.blazer.mq.api.service.FooService;
import com.blazer.mq.api.service.OtherException;
import com.blazer.mq.api.service.SomeException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class ServerFooService extends AbstractService implements FooService {
    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String echo(String a) {
        return a;
    }

    @Override
    public void send(String foo, Integer bar) throws SomeException, OtherException {
        if (foo == null) {
            throw new SomeException("foo is null");
        } else if (bar == null) {
            throw new OtherException("bar is null");
        } else {
            // Do nothing
            ServerFooService.log.debug(foo + bar);
        }
    }
}
