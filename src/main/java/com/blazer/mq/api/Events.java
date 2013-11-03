package com.blazer.mq.api;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
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

    @ToString
    public static class ErrorEvent implements Serializable {
        @Getter private Exception exception;

        public ErrorEvent(Exception exception) {
            this.exception = exception;
        }
    }
}
