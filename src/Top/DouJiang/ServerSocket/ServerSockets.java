package Top.DouJiang.ServerSocket;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SystemTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class ServerSockets implements Runnable{
    private ServerSocket ss=null;
    private ExecutorService es=null;
    private Socket s=null;
    @Override
    public void run() {
        try {
            ss=new ServerSocket(ConfigResult.ServerPort);
            es= Executors.newCachedThreadPool();
            while(ConfigResult.isRunning){
                s=ss.accept();
                Sockets sc=new Sockets(s);
                es.execute(sc);
            }
        } catch (IOException e) {
            SystemTools.Print("端口"+ConfigResult.ServerPort+"被占用!服务器关闭!",2,0);
            SystemTools.Close();
        }
    }
}
