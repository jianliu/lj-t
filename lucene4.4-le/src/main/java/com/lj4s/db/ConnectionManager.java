package com.lj4s.db;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-5
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class ConnectionManager {

    private static ConnectionManager instance;
    private static ComboPooledDataSource dataSource;

    private ConnectionManager() throws SQLException, PropertyVetoException {
        dataSource = new ComboPooledDataSource();

        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.100.17:3306/taobao_rank?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setInitialPoolSize(3);
        dataSource.setMinPoolSize(1);
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxStatements(50);
        dataSource.setMaxIdleTime(60);
        dataSource.setCheckoutTimeout(10000);
    }

    public static final ConnectionManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public synchronized final Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}  
