package Top.DouJiang.Rcon;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SystemTools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NicoNicoNi on 2017/8/12 0012.
 */
public class RconServer implements Runnable {
    private ServerSocket ss=null;
    private ExecutorService es=null;
    private Socket s=null;
    @Override
    public void run() {
        try {
            if(ConfigResult.RconHost==null) {
                ss = new ServerSocket(ConfigResult.RconPort);
            }else {
                ss =new ServerSocket(ConfigResult.RconPort,0,InetAddress.getByName(ConfigResult.RconHost));
            }
            es= Executors.newCachedThreadPool();
            while (true) {
                s = ss.accept();
                es.execute(new RconSockets(s));
            }
        } catch (IOException e) {
            SystemTools.Print("Rcon 模块异常服务器关闭! 异常Id: 1x001",2,0);
            SystemTools.Close();
        }
    }
}
