package com.blazer.lambda;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Getter
@Setter
@ToString
public class FooClass {
    private String field;

    public FooClass(String field) {
        this.field = field;
    }
}
