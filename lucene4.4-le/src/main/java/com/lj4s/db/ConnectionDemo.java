package com.lj4s.db;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-5
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionDemo {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConnectionDemo.class);


    public static void main(String[] args) throws SQLException {
        System.out.println("使用连接池................................");
        for (int i = 0; i < 1; i++) {
            long beginTime = System.currentTimeMillis();
            Connection conn = ConnectionManager.getInstance().getConnection();
            try {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM deal");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    long id = rs.getLong(1);
                    long nid = rs.getLong(2);
                    String tTitle = rs.getString(4);
                    System.out.println(String.format("id:%d,nid:%d,tTtile:%s", id, nid, tTitle));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("第" + (i + 1) + "次执行花费时间为:" + (endTime - beginTime));
        }

    }
}  

