package com.blazer.homework;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class CloserTest {
    @Test
    public void testCloseable() throws IOException {
        Closeable closeable = mock(Closeable.class);
        doNothing().when(closeable).close();

        Closeable closeableException = mock(Closeable.class);
        doThrow(new IOException()).when(closeableException).close();

        Closer.closeAll(closeable, closeableException);
        Closer.close(closeable);
        Closer.close(closeableException);
        Closer.close((Closeable)null);

    }

    @Test
    public void testStatements() throws SQLException {
        Statement closeable = mock(Statement.class);
        doNothing().when(closeable).close();

        Statement closeableException = mock(Statement.class);
        doThrow(new SQLException()).when(closeableException).close();

        Closer.closeAll(closeable, closeableException);
        Closer.close(closeable);
        Closer.close(closeableException);
        Closer.close((Statement)null);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection closeable = mock(Connection.class);
        doNothing().when(closeable).close();

        Connection closeableException = mock(Connection.class);
        doThrow(new SQLException()).when(closeableException).close();

        Closer.closeAll(closeable, closeableException);
        Closer.close(closeable);
        Closer.close(closeableException);
        Closer.close((Connection)null);
    }

    @Test
    public void testNull() {
        Closer.closeAll(new Object[]{null});
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        Closer.closeAll("Not closeable");
    }
}
