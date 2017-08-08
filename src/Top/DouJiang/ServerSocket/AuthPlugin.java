package Top.DouJiang.ServerSocket;

import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Tool.SystemTools;
import Top.DouJiang.Util.Mysqls.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class AuthPlugin {
    public AuthClass isAuth(Map<String,String> MsgMap){
        String User=MsgMap.get("User");
        String Pass = SocketTools.Base64Decrypt(MsgMap.get("Pass"));
        ConnectionPool.PooledConnection pool = StaticMap.pool;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuthClass ac=new AuthClass();
        if (SocketTools.isNumeric(User)) {
            try {
                ps = pool.getPrepareStatement("select * from user where id=?;");
                ps.setString(1, User);
                rs = ps.executeQuery();
            } catch (SQLException e) {
                //
            }
        } else {
            return ac;
         /*
          try {
                 ps = pool.getPrepareStatement("select * from user where user=?;");
                   ps.setString(1, User);
                   rs = ps.executeQuery();
            } catch (SQLException e) {
              }
               */
        }
        try {
            if (rs.next()) {
                String pass2 = rs.getString("pass");
                String salt = rs.getString("salt");
                if (pass2 == null || salt == null) {
                    return ac;
                    //
                }
                Pass = SocketTools.StringToMD5(SocketTools.StringToMD5(Pass) + salt);
                if (pass2.equalsIgnoreCase(Pass)) {
                    ac.setId(rs.getString("id"));
                    ac.setAuth(true);
                    SystemTools.Print("Id: "+ac.getId()+" 登入成功!",1,1);
                    StaticMap.n++;
                    //成功登入
                }
            }
        } catch (SQLException e) {
            return  ac;
        }
        return ac;
    }
}
