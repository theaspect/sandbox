package com.blazer.homework;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Application {
    private static final String CONNECTION_STRING = "jdbc:h2:file:dbfile;AUTOCOMMIT=OFF;MVCC=TRUE";
    private static final String INIT_TABLE = "CREATE TABLE IF NOT EXISTS data (key VARCHAR(64) PRIMARY KEY, value LONG)";

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_STRING, "SA", "");
        connection.prepareStatement(INIT_TABLE).execute();
        return connection;
    }

    public static void main(String argv[]) {
        if (argv.length < 1) {
            System.out.println("Specify file path with data");
            return;
        }

        Connection connection = null;
        BufferedReader reader = null;

        try {
            connection = getConnection();
            Dao dao = new Dao(connection);

            reader = new BufferedReader(new FileReader(new FileSelector(new File(argv[0])).getMaxFile()));
            dao.putData(new DataReader(new Parser(" ")).parse(reader));

            connection.commit();

            System.out.print(dao.getData());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.close(connection);
            Closer.close(reader);
        }
    }
}
