package Top.DouJiang.Main;

import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Util.Mysqls.ConnectionPool;
import Top.DouJiang.Util.Mysqls.DBManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class PServerTest {
    public static void main(String[] args) {
        ConnectionPool.PooledConnection pool = DBManager.getConnection("127.0.0.1", 3306, "test4", "root", "diantong");
        for (int i = 0; i < 100; i++) {
            try {
                PreparedStatement ps = pool.getPrepareStatement("insert into user(user,pass,id,salt) values(?,?,?,?);");
                String salt = SocketTools.getRandomString(16);
                String pass = SocketTools.MD5PassWord("753159" + i, salt);
                String user = "QAQAQ" + i;
                String id = String.valueOf(i);
                ps.setString(2, pass);
                ps.setString(1, user);
                ps.setString(3, id);
                ps.setString(4, salt);
                ps.executeUpdate();
                System.out.println("插入数据 Id: " + id + " 账号:" + "QAQAQ" + i + " 密码: " + pass + " 盐: " + salt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
