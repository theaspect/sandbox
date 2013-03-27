package com.blazer.scenario;

import com.blazer.scenario.call.CallRouter;
import com.blazer.scenario.domain.Record;
import com.blazer.scenario.domain.User;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.event.BridgeEvent;
import com.blazer.scenario.event.CreateEvent;
import com.blazer.scenario.event.DestroyEvent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class RecordTest {
    private static UserDao userDao;

    @BeforeClass
    public static void init() {
        // Here must go mocking database as in com.blazer.homework.*
        // but we simplify
        // ...
        //

        User user1 = new User();
        user1.setId(1L);
        user1.setName("user1");
        user1.setTerminal("terminal1");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("user2");
        user2.setTerminal("terminal2");

        userDao = mock(UserDao.class);
        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userDao.findUserByName("user1")).thenReturn(user1);
        when(userDao.findUserByName("user2")).thenReturn(null);
        when(userDao.findUserByTerminal("terminal1")).thenReturn(user1);
        when(userDao.findUserByTerminal("terminal2")).thenReturn(user2);
    }

    @SuppressWarnings("unchecked")
    protected List<Object> loadEvents(String yaml) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(yaml);
        return (List<Object>) new Yaml().load(is);
    }

    protected void testEventFlow(List<Object> events) {
        HashMap<Long, User> userHashMap = new HashMap<Long, User>();
        for (User u : userDao.findAll()) {
            userHashMap.put(u.getId(), u);
        }

        CallRouter er = new CallRouter();
        for (Object event : events) {
            if (event instanceof CreateEvent) {
                er.addEvent((CreateEvent) event);
            } else if (event instanceof BridgeEvent) {
                er.addEvent((BridgeEvent) event);
            } else if (event instanceof DestroyEvent) {
                er.addEvent((DestroyEvent) event);
            } else if (event instanceof Record) {
                Record record = er.pullRecord(userDao);
                ((Record) event).setUser(userHashMap.get(((Record) event).getUser().getId()));
                compare((Record) event, record);
            }
        }
        assertFalse(er.hasNewRecord());
    }

    protected void compare(Record expected, Record actual) {
        // Here coming complec comparison of all fields
        // ...
        //
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUser(), actual.getUser());
    }

    @Test
    public void simpleCallTest() {
        testEventFlow(loadEvents("com/blazer/scenario/SimpleCall.yaml"));
    }

    @Test
    public void complexCallTest() {
        // Here go another scenario
        // ...
        //
    }

    // 50 more scenarios
    // ...
    //
}
