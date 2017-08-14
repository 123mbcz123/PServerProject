package Top.DouJiang.Test;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Tool.ZipUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class TestSocket extends Thread {
    private DataOutputStream dos = null;
    public void run() {
        try {
            Socket s = new Socket("127.0.0.1", 6666);
            //s.setKeepAlive(true);
            dos = new DataOutputStream(s.getOutputStream());
            new RecvThread(s).start();
            Send("Login 123456789");
        } catch (IOException e) {
        }
    }
    public void Send(Map<String, String> map) {
        Send(SocketTools.MapToJson(map));

    }
    public void Send(String str) {
        System.out.println("发送数据:"+str);
        try {
            /*
            压缩实现
             */
                dos.writeUTF("["+str+"]");
                dos.flush();
        } catch (IOException e) {
        }
    }

    public static class RecvThread extends Thread {
        DataInputStream dis = null;
        Socket s1 = null;

        public RecvThread(Socket s1) {
            this.s1 = s1;
        }

        public void run() {
            try {
                dis = new DataInputStream(s1.getInputStream());
                while (true) {
                    System.out.println("接受数据: "+dis.readUTF());
                }
            } catch (IOException e) {
                //
            }
        }
    }
}
