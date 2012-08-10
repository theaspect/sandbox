package com.blazer.homework;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@EqualsAndHashCode
@ToString
public class Domain {
    public String key;
    public Long value;

    public Domain(String key, Long value) {
        this.key = key;
        this.value = value;
    }
}
