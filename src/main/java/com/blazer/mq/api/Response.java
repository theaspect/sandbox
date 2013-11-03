package com.blazer.mq.api;

import lombok.Getter;

import java.io.Serializable;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class Response implements Serializable {
    @Getter private String guid;
    @Getter private Serializable result;
    @Getter private String exception;
    @Getter private String message;

    public Response(String guid, Serializable result) {
        this.guid = guid;
        this.result = result;
    }

    public Response(String guid, String exceptionClass, String exceptionMessage) {
        this.guid = guid;
        this.exception = exceptionClass;
        this.message = exceptionMessage;
    }

    public boolean hasException() {
        return exception != null;
    }
}
