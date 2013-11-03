package com.blazer.mq.api;

import javax.jms.JMSException;

/** @author Constantine Linnick <theaspect@gmail.com> */
public interface Client {
    public static final String RMI = "RMI";

    void sendRequest(Request message) throws JMSException;

    Response getResponse() throws JMSException;
}
