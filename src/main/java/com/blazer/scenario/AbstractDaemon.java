package com.blazer.scenario;

import java.util.HashMap;

/**
 * External API class
 * <p/>
 * This class is only for demonstration of first layer
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public abstract class AbstractDaemon {
    public void subscribe(String... events) {
        // Here go external API subscription code
    }

    /**
     * Event listener, you must override it to receive raw events
     *
     * @param rawEvent
     */
    public abstract void onEvent(HashMap<String, String> rawEvent);
}
