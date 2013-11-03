package com.blazer.mq.api.service;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class Events {
    private Events() {
    }

    @ToString
    public static class SomeEvent implements Serializable {
        @Getter private String message;

        public SomeEvent(String message) {
            this.message = message;
        }
    }
}
