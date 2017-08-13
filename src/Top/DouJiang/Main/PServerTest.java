package Top.DouJiang.Main;

import Top.DouJiang.Tool.SystemTools;
import Top.DouJiang.Util.Mysqls.ConnectionPool;
import Top.DouJiang.Util.Mysqls.DBManager;
import Top.DouJiang.plugin.Plugin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class PServerTest {
    public static void main(String[] args) {
        ConnectionPool.PooledConnection pool= DBManager.getConnection("127.0.0.1",3306,"test4","root","diantong");
        Set<String> MemberSet=new HashSet<>();
        Set<String> ManagerSet=new HashSet<>();
        for(int i=0;i<100;i++) {
            MemberSet.add(String.valueOf(i));
        }
        ManagerSet.add(String.valueOf(1));
        ManagerSet.add(String.valueOf(2));
        ManagerSet.add(String.valueOf(3));
        ManagerSet.add(String.valueOf(4));
        ManagerSet.add(String.valueOf(5));
        ManagerSet.add(String.valueOf(6));
        ManagerSet.add(String.valueOf(7));
        ManagerSet.add(String.valueOf(8));
        ManagerSet.add(String.valueOf(9));
        ManagerSet.add(String.valueOf(10));
        try {

            PreparedStatement ps=null;
            /*
            pool.getPrepareStatement("insert into groups(Group_Id,Group_Member,Group_Master,Group_Manager) values(?,?,?,?);");
            ps.setString(1,"1");
            ps.setObject(2,MemberSet);
            ps.setString(3,"0");
            ps.setObject(4,ManagerSet);
            ps.executeUpdate();
            */
            ObjectInputStream in =null;
            Object obj =null;
            ps=pool.getPrepareStatement("select * from groups where Group_Id=1;");
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                in = new ObjectInputStream(rs.getBlob("Group_Manager").getBinaryStream());
                obj = in.readObject();
                in.close();
            }
            Set<String> sets=(Set<String>)obj;
            System.out.println(sets);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
