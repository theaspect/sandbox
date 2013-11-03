package com.blazer.mq.api;

import javax.jms.Destination;
import java.io.Serializable;

/** @author Constantine Linnick <theaspect@gmail.com> */
public interface Server {
    public static final String RMI = "RMI";

    Response receiveMessage(Request message);

    void sendResponse(Destination jmsReplyTo, Serializable message);
}
