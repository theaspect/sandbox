package com.blazer.mq.api;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public interface Api {
    public static final String RMI = "RMI";
    public static final String EVENTS = "EVENTS";

    String get() throws SomeException;

    String echo(String a);

    void send(String foo, Integer bar);
}
