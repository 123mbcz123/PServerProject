package Top.DouJiang.APIs;

import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.ServerSocket.Sockets;
import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Util.Mysqls.ConnectionPool;

import java.util.Date;
import java.util.Set;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class API {
    String plugin=null;
    String TaskId=null;
    public API(String pl,String id) {
        plugin=pl;
        TaskId=id;
    }
    public  void Print(String str,int level){
        Date d=new Date();
        if(level<= ConfigResult.PrintLevel) {
            System.out.println("[插件: " + plugin + " 时间: " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "]" + str);
        }
;   }
    public void Send(){

    }
    public static ConnectionPool.PooledConnection GetPooledConnection(){
        return StaticMap.pool;
    }
    /*
    根据QQ号获取Sockets 对象
    这将是本程序最大的败笔 (最耗费资源的地方
    后期将进行优化 类似人工智能? #视情况而定
     */
    public static Sockets GetSocketThroughId(String Id){
        Set<Sockets> Sockets_Set=StaticMap.Sockets_Set;
        for(Sockets s:Sockets_Set){
            if(s.getId()!=null&&s.getId().equalsIgnoreCase(Id)){
                return s;
            }
        }
        return null;
    }
    }

