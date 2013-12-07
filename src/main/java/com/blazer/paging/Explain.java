package com.blazer.paging;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class Explain {
    private final Connection connection;

    public static void main(String[] args) throws SQLException {
        Explain app = new Explain();
        log.debug("LJ+Distinct+Order+Lim");
        app.getRows("select top ? distinct parentfilt0_.id as id1_9_, parentfilt0_.nonIndex as nonIndex2_9_, parentfilt0_.value as value3_9_ from parent_filter parentfilt0_ left outer join child_filter child1_ on parentfilt0_.id=child1_.parent_id order by parentfilt0_.value", 10);
        log.debug("Filter+Lim");
        app.getRows("select top ? distinct parentfilt0_.id as id1_9_, parentfilt0_.nonIndex as nonIndex2_9_, parentfilt0_.value as value3_9_ from parent_filter parentfilt0_ left outer join child_filter child1_ on parentfilt0_.id=child1_.parent_id where parentfilt0_.value between ? and ? order by parentfilt0_.value", 10, -2147483648, 2147483647);
        log.debug("FilterBoth+Lim");
        app.getRows("select top ? distinct parentfilt0_.id as id1_9_, parentfilt0_.nonIndex as nonIndex2_9_, parentfilt0_.value as value3_9_ from parent_filter parentfilt0_ left outer join child_filter child1_ on parentfilt0_.id=child1_.parent_id where (parentfilt0_.value between ? and ?) and (child1_.value between ? and ?) order by parentfilt0_.value", 10, -2147483648, 2147483647, -2147483648, 2147483647);
        log.debug("FilterBothSmart Prefetch");
        app.getRows("select top ? parentfilt0_.id as col_0_0_, parentfilt0_.value as col_1_0_ from parent_filter parentfilt0_ where parentfilt0_.value between ? and ? order by parentfilt0_.value", 10, -2147483648, 2147483647);
        log.debug("FilterBothSmart Fetch");
        app.getRows("select distinct parentfilt0_.id as id1_9_, parentfilt0_.nonIndex as nonIndex2_9_, parentfilt0_.value as value3_9_ from parent_filter parentfilt0_ left outer join child_filter child1_ on parentfilt0_.id=child1_.parent_id where (parentfilt0_.id in (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)) and (child1_.value between ? and ?) order by parentfilt0_.value", 6441, 8740, 289, 9752, 720, 7219, 3254, 2324, 1808, 3089, -2147483648, 2147483647);
    }

    public Explain() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:file:target/npo", "ca", "");
    }

    private void getRows(String statement, Object... args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("EXPLAIN ANALYZE " + statement);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            List<String> cols = new LinkedList<>();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                cols.add(rs.getString(i + 1));
            }
            log.debug("{}", cols);
        }
    }
}
