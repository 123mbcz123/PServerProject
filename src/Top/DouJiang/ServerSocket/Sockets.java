package Top.DouJiang.ServerSocket;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Main.CallEventClass;
import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Tool.SystemTools;
import Top.DouJiang.Tool.ZipUtils;
import Top.DouJiang.plugin.ChatEvents;
import Top.DouJiang.plugin.CommandClass;
import Top.DouJiang.plugin.CommandEvents;
import Top.DouJiang.plugin.TaskClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class Sockets implements Runnable {
    private Socket s = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String Id = null;
    private CallEventClass cec = new CallEventClass();
    private String str = null;
    private boolean isAuth = false;

    public Sockets(Socket s) {
        this.s = s;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public void Close() {
        StaticMap.Sockets_Set.remove(this);
        if (s != null) {
            try {
                s.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        if (dos != null) {
            try {
                dos.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (dis != null) {
            try {
                dis.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            SystemTools.Print("客户IP:" + s.getInetAddress().getHostAddress() + "加入惹连接!", 1, 1);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            while (ConfigResult.isRunning) { //连续获取数据
                String ReadStr = dis.readUTF();
                if (ConfigResult.isUseZip) {
                    if (ConfigResult.TrueUseZipOrFalseUseGzip) {
                        ReadStr = ZipUtils.unzip(ReadStr);
                    } else {
                        ReadStr = ZipUtils.ungzip(ReadStr);
                    }
                }
                List<String> Msg_List = SocketTools.TurnToList(ReadStr); //防止包粘连
                for (int i = 0; i < Msg_List.size(); i++) {
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
                    if(Cmd.equalsIgnoreCase("Chat")) {
                        ChatEvent(MsgMap);
                        continue;
                    }
                    CommandClass cc=new CommandClass(this,MsgMap);
                    CallEventClass cec=new CallEventClass();
                    cec.CallCommandRunner(cc);
                }
            }
        } catch (IOException e) {
            SystemTools.Print("客户IP:" + s.getInetAddress().getHostAddress() + "断开惹连接!", 1, 1);
        }
        Close();
    }
    private void ChatEvent(Map<String,String> MsgMap){
        if (!isAuth) {
            //SystemTools.Print("IP: " + s.getInetAddress().getHostAddress() + " Id: " + Id + " 尝试不登入发送信息!", 2, 1);
            Map<String,String> NotLoginMap=new HashMap<>();
            NotLoginMap.put("Cmd","Return");
            NotLoginMap.put("TypeId","Auth_004");
            Send(NotLoginMap);
            return;
        }
        String ToId = MsgMap.get("ToId");
        String SendMsg = SocketTools.Base64Decrypt(MsgMap.get("Msg"));
        int Type = -1;
        if (MsgMap.get("Type").equalsIgnoreCase("1")) {
            Type = 1;
        } else if (MsgMap.get("Type").equalsIgnoreCase("2")) {
            Type = 2;
        }
        if (ToId == null || (Type != 1 && Type != 2)) {
            return;
        }
        TaskClass tc = new TaskClass(Id, ToId, Type, SendMsg);
        cec.CallChatEvent(tc);
    }
    public void Send(Map<String, String> map) {
        Send(SocketTools.MapToJson(map));

    }
    public void Send(String str) {
        try {
            /*
            压缩实现
             */
            if (ConfigResult.isUseZip) {
                if (ConfigResult.TrueUseZipOrFalseUseGzip) {
                    dos.writeUTF("["+ZipUtils.unzip(str)+"]");
                    dos.flush();
                } else {
                    dos.writeUTF("["+ZipUtils.ungzip(str)+"]");
                    dos.flush();
                }
            } else {
                dos.writeUTF("["+str+"]");
                dos.flush();
            }
        } catch (IOException e) {
        }
    }
}
