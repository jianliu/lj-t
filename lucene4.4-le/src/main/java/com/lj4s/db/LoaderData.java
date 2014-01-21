package com.lj4s.db;

import com.lj4s.bean.Deal;
import com.lj4s.handle.Translator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-5
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class LoaderData {

    public static Deal[] loadDeal() throws SQLException {
        java.util.List<Deal> list = new java.util.ArrayList<Deal>();
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
                    Deal deal = new Deal();
                    deal.setId(id);
                    deal.setNumIid(nid);
                    deal.setTaobaoTitle(tTitle);
                    list.add(deal);
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
        return list.toArray(new Deal[list.size()]);
    }

    public <T> T[] loadIn(String sql, Translator<T> translator, String orderClause, int offset, int limit) throws SQLException {
        sql = sql + orderClause + String.format("limit %d,%d ", offset, limit);
        java.util.List<T> list = new java.util.ArrayList<T>();
        System.out.println("使用连接池................................");
        for (int i = 0; i < 1; i++) {
            long beginTime = System.currentTimeMillis();
            Connection conn = ConnectionManager.getInstance().getConnection();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    T t = translator.translateObj(rs);
                    list.add(t);
//                    System.out.println(String.format("id:%d,nid:%d,tTtile:%s", id, nid, tTitle));
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
        return (T[]) list.toArray();
    }


}
