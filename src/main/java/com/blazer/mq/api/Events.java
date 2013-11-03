package com.blazer.mq.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Events {
    private Events() {
    }

    @Getter
    @Setter
    public static class SomeEvent {
        private String foo;
        private String bar;

        public SomeEvent(String foo, String bar) {
            this.foo = foo;
            this.bar = bar;
        }
    }
}
