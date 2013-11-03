package com.blazer.mq.api;

import lombok.Getter;

import java.io.Serializable;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class Request implements Serializable {
    @Getter private String guid;
    @Getter private String service;
    @Getter private String method;
    @Getter private Serializable args[];

    public Request(String guid, String service, String method, Serializable... args) {
        this.guid = guid;
        this.service = service;
        this.method = method;
        this.args = args;
    }
}
