package Top.DouJiang.Main;

import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Tool.SocketTools;
import Top.DouJiang.Tool.SystemTools;
import Top.DouJiang.Util.Mysqls.ConnectionPool;


/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class PServer {
    public static void main(String[] args){
        SystemTools.Initialize();
        ConnectionPool.PooledConnection pool= StaticMap.pool;
        String salt= SocketTools.getRandomString(16);
        String user="QAQ1";
        String pass="QAQ1";
        String finalPass=(SocketTools.StringToMD5(SocketTools.StringToMD5("QAQ1")+salt));
        System.out.println("FinalPass="+finalPass);
        System.out.println("salt="+salt);
        /*
        long startTime=System.nanoTime();   //获取开始时间
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
        */

    }
}
