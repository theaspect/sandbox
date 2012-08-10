package com.blazer.homework;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class DaoTest {
    private Connection connection;

    private static final String CONNECTION_STRING = "jdbc:h2:mem:;AUTOCOMMIT=OFF;MVCC=TRUE";
    private static final String INIT_TABLE = "CREATE TABLE IF NOT EXISTS data (key VARCHAR(64) PRIMARY KEY, value LONG)";

    public void loadFixtures(Connection connection, String file) throws IOException, SQLException {
        URL url = getClass().getClassLoader().getResource(file);
        if (url == null) {
            throw new RuntimeException("Fixture not found " + file);
        }
        BufferedReader sqlFixtures = new BufferedReader(new FileReader(url.getFile()));
        String row;
        while ((row = sqlFixtures.readLine()) != null) {
            connection.prepareStatement(row).execute();
        }
    }

    @Before
    public void initDB() throws SQLException, IOException {
        connection = DriverManager.getConnection(CONNECTION_STRING, "SA", "");
        connection.prepareStatement(INIT_TABLE).execute();
        loadFixtures(connection, "com/blazer/homework/fixtures.sql");
    }

    @After
    public void dropDB() throws SQLException, IOException {
        connection.rollback();
        Closer.close(connection);
    }

    @Test
    public void testGetData() throws SQLException {
        Dao dao = new Dao(connection);
        Assert.assertEquals(new HashSet<Domain>() {{
            add(new Domain("qwer", 123L));
            add(new Domain("asdf", 456L));
            add(new Domain("zxcv", 789L));
        }}, dao.getData());
    }

    @Test
    public void testPutData() throws SQLException {
        Dao dao = new Dao(connection);
        dao.putData(new HashSet<Domain>() {{
            add(new Domain("qwer", 123L));
            add(new Domain("fdsa", 456L));
            add(new Domain("zxcv", 789L));
            add(new Domain("qaz", 111L));
        }});

        Assert.assertEquals(new HashSet<Domain>() {{
            add(new Domain("fdsa", 456L));
            add(new Domain("asdf", 456L));
            add(new Domain("qaz", 111L));
        }}, dao.getData());
    }
}
