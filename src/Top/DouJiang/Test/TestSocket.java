package Top.DouJiang.Test;

import Top.DouJiang.Tool.SocketTools;
import com.sun.istack.internal.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class TestSocket  extends Thread{
    private String user=null;
    private String pass=null;
    public TestSocket(String user,String pass){
        this.user=user;
        this.pass=pass;
    }
    public void run(){
        DataOutputStream dos=null;
        TestThread.all++;
        try {
            Socket s=new Socket("192.168.8.130",2333);
            //s.setKeepAlive(true);
            dos=new DataOutputStream(s.getOutputStream());
            //new RecvThread(s).start();
                Map<String,String> CmdMap=new HashMap<>();
                CmdMap.put("Cmd","Auth");
                CmdMap.put("User",user);
                CmdMap.put("Pass",SocketTools.Base64Encryption(pass));
                dos.writeUTF("["+SocketTools.MapToJson(CmdMap)+"]");
                dos.flush();
        } catch (IOException e) {
           TestThread.fail++;
        }
    }
    public static class RecvThread extends Thread{
        DataInputStream dis=null;
        Socket s1=null;
        public RecvThread(Socket s1){
            this.s1=s1;
        }
        public void run(){
            try {
                dis=new DataInputStream(s1.getInputStream());
                while(true){
                    dis.readUTF();
                }
            } catch (IOException e) {
                //
            }
        }
    }
}
