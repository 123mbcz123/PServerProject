package Top.DouJiang.ServerSocket;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Main.CallEventClass;
import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Tool.SystemTools;
import Top.DouJiang.plugin.TaskClass;
import Top.DouJiang.Util.Mysqls.ConnectionPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class Sockets implements Runnable {
    private Socket s = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    public String getId() {
        return Id;
    }

    private String Id=null;
    private CallEventClass cec=new CallEventClass();
    //private GZIPInputStream gis=null;
    // private GZIPOutputStream gos=null;
    private String str = null;
    private boolean isAuth = false;

    public Sockets(Socket s) {
        this.s = s;

    }

    @Override
    public void run() {
        StaticMap.Sockets_Set.add(this);
        CallEventClass ce = new CallEventClass();
        try {
            SystemTools.Print("客户IP:" + s.getInetAddress().getHostAddress() + "加入惹连接!", 1, 1);
            //s.setKeepAlive(true);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            while (ConfigResult.isRunning) { //连续获取数据
                String ReadStr = dis.readUTF();
                List<String> Msg_List=SocketTools.TurnToList(ReadStr); //防止包粘连
                for(int i=0;i<Msg_List.size();i++) {
                    str = Msg_List.get(i); //遍历所有包
                    Map<String, String> MsgMap = SocketTools.JsonToMap(str);
                    if (MsgMap == null) {
                        // SystemTools.Print("IP", 1, 1);
                        continue;
                    }
                    String Cmd = MsgMap.get("Cmd");
                    if (Cmd == null) {
                        //SystemTools.Print("IP",1,1);
                        continue;
                    }
                    /*
                    对指令进行处理
                     */
                    switch (Cmd) {
                        case "Auth":
                            String User = SocketTools.Base64Decrypt(MsgMap.get("User"));
                            String Pass = SocketTools.Base64Decrypt(MsgMap.get("Pass"));
                            if (User == null || Pass == null) {
                                //输出
                                continue;
                            }
                            ConnectionPool.PooledConnection pool = StaticMap.pool;
                            PreparedStatement ps = null;
                            ResultSet rs = null;
                            if (SocketTools.isNumeric(User)) {
                                try {
                                    ps = pool.getPrepareStatement("select * from user where id=?;");
                                    ps.setString(1, User);
                                    rs = ps.executeQuery();
                                } catch (SQLException e) {
                                    //
                                }
                            } else {
                                try {
                                    ps = pool.getPrepareStatement("select * from user where user=?;");
                                    ps.setString(1, User);
                                    rs = ps.executeQuery();
                                } catch (SQLException e) {
                                    //
                                }
                            }
                            try {
                                if (rs.next()) {
                                    String pass2 = rs.getString("pass");
                                    String salt = rs.getString("salt");
                                    if (pass2 == null || salt == null) {
                                        continue;
                                        //
                                    }
                                    Pass = SocketTools.StringToMD5(SocketTools.StringToMD5(Pass) + salt);
                                    if (pass2.equalsIgnoreCase(Pass)) {
                                        isAuth = true;
                                        //成功登入
                                        Id=rs.getString("id");
                                    }
                                }
                            } catch (SQLException e) {
                                //
                            }
                            break;
                        case "Chat":
                            if(!isAuth){
                                ///返回没有登入
                                continue;
                            }
                            String ToId=MsgMap.get("ToId");
                            String SendMsg=SocketTools.Base64Decrypt(MsgMap.get("Msg"));
                            int Type =-1;
                            if(MsgMap.get("Type").equalsIgnoreCase("1")){
                                Type=1;
                            }else if(MsgMap.get("Type").equalsIgnoreCase("2")){
                                Type=2;
                            }
                            if(ToId==null||SendMsg==null||(Type!=1&&Type!=2)){
                                continue;
                            }
                            TaskClass tc=new TaskClass(Id,ToId,Type,SendMsg);
                            cec.CallChatEvent(tc);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            // SystemTools.Print("客户IP:"+s.getInetAddress().getHostAddress()+"断开惹连接!",1,1);
        }
        StaticMap.Sockets_Set.remove(this);
    }

    public void Send(String str) {
        try {
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
        }
    }
}
