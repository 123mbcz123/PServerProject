package Top.DouJiang.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class TestSocket  extends Thread{
    public static void main(String[] args){
        for(int i=0;i<10000;i++){
            new TestSocket().start();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("启动完成");
    }
    public void run(){
        DataOutputStream dos=null;
        try {
            Socket s=new Socket("127.0.0.1",2333);
            //s.setKeepAlive(true);
            dos=new DataOutputStream(s.getOutputStream());
            new RecvThread(s).start();
            for(;;) {
                dos.writeUTF("Test,Test");
                dos.flush();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           }
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
}
