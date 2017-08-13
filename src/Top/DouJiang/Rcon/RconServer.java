package Top.DouJiang.Rcon;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SystemTools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by NicoNicoNi on 2017/8/12 0012.
 */
public class RconServer implements Runnable {
    private ServerSocket ss=null;
    @Override
    public void run() {
        try {
            if(ConfigResult.RconHost==null) {
                ss = new ServerSocket(ConfigResult.RconPort);
            }else {
                ss =new ServerSocket(ConfigResult.RconPort,1000000,InetAddress.getByName(ConfigResult.RconHost));
            }

        } catch (IOException e) {
            SystemTools.Print("Rcon 模块异常服务器关闭! 异常Id: 1x001",2,0);
            SystemTools.Close();
        }
    }
}
