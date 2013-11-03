package com.blazer.mq.api.service;

/** @author Constantine Linnick <theaspect@gmail.com> */
public interface FooService {
    String get();

    String echo(String a);

    void send(String foo, Integer bar) throws SomeException, OtherException;
}
