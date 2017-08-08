package Top.DouJiang.Tool;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Static.StaticMap;
import com.sun.deploy.net.proxy.StaticProxyManager;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class KeepThread extends Thread {
    public void run(){
        while(ConfigResult.isRunning){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
            System.out.println("注册数量为: "+ StaticMap.n);
        }
    }
}
