package Top.DouJiang.Rcon;

import Top.DouJiang.Command.ConsoleCommand;
import Top.DouJiang.Config.Config;
import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SocketTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/12 0012.
 */
public class RconSockets implements Runnable {
    private Socket s=null;
    private DataOutputStream dos=null;
    private DataInputStream dis=null;
    private boolean isAuth=false;
    private boolean isRunning=true;
    @Override
    public void run() {
        if(ConfigResult.isUseIpWhiteList){
            /*
            白名单验证模块
             */
            String host=s.getInetAddress().getHostAddress();
            boolean isHave=false;
            for(String s:ConfigResult.WhiteList){
                if(s.equalsIgnoreCase(host)){
                    isHave=true;
                    break;
                }
            }
            if(!isHave){
                return;
            }
        }
        try {
            dos=new DataOutputStream(s.getOutputStream());
            dis=new DataInputStream(s.getInputStream());
            System.out.println("链接开始");
            while(isRunning){
                List<String> strs= SocketTools.TurnToList(dis.readUTF());
                System.out.println("读取数据: "+strs);
                for(String str:strs){
                    String[] Cmds=str.split("\\ ");
                    if(Cmds.length==0){
                        continue;
                    }
                    String Cmd=Cmds[0];
                    if(Cmd==null){
                        continue;
                    }
                    if(Cmd.equalsIgnoreCase("System")){
                        if(Cmds.length==1){
                            continue;
                        }
                        switch (Cmds[1]){
                            case "Login":
                                if(Cmds.length!=3){
                                    continue;
                                }
                                isAuth=isSuccessfulAuth(Cmds[2]);
                                break;
                            case "Close":
                                Close();
                                break;

                        }
                    }
                    if(!Cmd.equalsIgnoreCase("System")){
                        if(!isAuth){
                            Map<String ,String> NotLoginMap=new HashMap<>();
                            NotLoginMap.put("Cmd","Auth");
                            NotLoginMap.put("TypeId","0x004");
                            Send(NotLoginMap);
                            continue;
                        }
                        ConsoleCommand cc=new ConsoleCommand(Cmds,2);
                        cc.CallConsoleCommand();
                        continue;
                    }


                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
    public void Close(){
        isRunning=false;
        if(dos!=null){
            try {
                dos.close();
            } catch (IOException e) {
                //
            }
        }
        if(dis!=null){
            try {
                dis.close();
            } catch (IOException e) {
                //
            }
        }
        if(s!=null){
            try {
                s.close();
            } catch (IOException e) {
                //
            }
        }
    }
    public boolean isSuccessfulAuth(String key){
        boolean isAuth=false;
        if(ConfigResult.RconKey.equalsIgnoreCase(key)){
            isAuth =true;
        }
        if(isAuth){
            Map<String ,String> SuccessfulLoginMap=new HashMap<>();
            SuccessfulLoginMap.put("Cmd","Auth");
            SuccessfulLoginMap.put("TypeId","0x002");
            Send(SuccessfulLoginMap);
        }else {
            Map<String ,String> failLoginMap=new HashMap<>();
            failLoginMap.put("Cmd","Auth");
            failLoginMap.put("TypeId","0x001");
            Send(failLoginMap);
        }
        System.out.println("Login="+isAuth);
        return isAuth;
    }
    public RconSockets(Socket s){
        this.s=s;
    }
    public void Send(Map<String,String> CmdMap){
        System.out.println("发送数据: "+SocketTools.MapToJson(CmdMap));
        try {
            dos.writeUTF("["+SocketTools.MapToJson(CmdMap)+"]");
            dos.flush();
        } catch (IOException e) {
            //
        }
    }
}
