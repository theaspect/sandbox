package com.blazer.mq.api;

import java.io.Serializable;

/** @author Constantine Linnick <theaspect@gmail.com> */
public interface Notificator {
    public static final String EVENTS = "EVENTS";

    void sendNotification(Serializable event);
}
