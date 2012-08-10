package com.blazer.homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Dao {
    private static final String SELECT_ALL = "SELECT * FROM data";
    private static final String SELECT = "SELECT * FROM data WHERE key = ?";
    private static final String DELETE = "DELETE FROM data WHERE key = ?";
    private static final String INSERT = "INSERT INTO data (key, value) VALUES (?, ?)";

    private Connection connection;

    private PreparedStatement selectStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement insertStatement;


    public Dao(Connection connection) {
        this.connection = connection;
    }

    private void initStatements() throws SQLException {
        selectStatement = connection.prepareStatement(SELECT);
        deleteStatement = connection.prepareStatement(DELETE);
        insertStatement = connection.prepareStatement(INSERT);
    }

    public void putData(Set<Domain> data) throws SQLException {
        try {
            initStatements();
            for (Domain d : data) {
                upsertRow(d.key, d.value);
            }
            deleteStatement.executeBatch();
            insertStatement.executeBatch();
        } finally {
            Closer.closeAll(selectStatement, deleteStatement, insertStatement);
        }
    }

    public Set<Domain> getData() throws SQLException {
        Set<Domain> data = new HashSet<Domain>();

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data.add(new Domain(resultSet.getString(1), resultSet.getLong(2)));
            }
        } finally {
            Closer.close(preparedStatement);
        }
        return data;
    }

    private void upsertRow(String key, Long value) throws SQLException {
        selectStatement.setString(1, key);
        ResultSet select = selectStatement.executeQuery();
        if (select.next()) {
            deleteStatement.setString(1, key);
            deleteStatement.addBatch();
        } else {
            insertStatement.setString(1, key);
            insertStatement.setLong(2, value);
            insertStatement.addBatch();
        }
    }
}
