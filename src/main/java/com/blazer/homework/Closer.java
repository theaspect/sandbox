package com.blazer.homework;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Closer {
    public static void closeAll(Object... closeables) {
        for (Object c : closeables) {
            if (c == null) {
                continue;
            }

            if (c instanceof Connection) {
                close((Connection) c);
            } else if (c instanceof Statement) {
                close((Statement) c);
            } else if (c instanceof Closeable) {
                close((Closeable) c);
            } else {
                throw new RuntimeException("Unknown class :" + c);
            }
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // do nothing
        }
    }

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // do nothing
        }
    }

    public static void close(Closeable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException e) {
            // do nothing
        }
    }
}
