package Top.DouJiang.Util.Mysqls;

/**
 * Created by NicoNicoNi on 2017/5/30 0030.
 */

import java.sql.SQLException;

public class DBManager {
    private static ConnectionPool.PooledConnection conn;
    private static ConnectionPool connectionPool;
    private static DBManager inst;

    public DBManager(String host, int port, String db, String user, String pass) {
        if (inst != null)
            return;
        // TODO Auto-generated constructor stub

        String connStr = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", host, port,
                db);
        // System.out.println("SQL: "+connStr);
        connectionPool = new ConnectionPool("com.mysql.cj.jdbc.Driver"/*"com.mysql.jdbc.Driver"*/, connStr, user, pass);
        try {
            connectionPool.createPool();
            inst = this;


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static ConnectionPool.PooledConnection getConnection(String host, int port, String db, String user, String pass) {
        if (inst == null)
            new DBManager(host, port, db, user, pass);
        try {
            conn = connectionPool.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

    public void close() {
        try {
            connectionPool.closeConnectionPool();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
