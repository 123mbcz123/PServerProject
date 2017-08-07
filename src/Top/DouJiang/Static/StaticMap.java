package Top.DouJiang.Static;

import Top.DouJiang.ServerSocket.Sockets;
import Top.DouJiang.plugin.Plugin;
import Top.DouJiang.Util.Mysqls.ConnectionPool;
import Top.DouJiang.Util.Redis.RedisUtil;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class StaticMap {
    public static List<Plugin> getMainClass_List() {
        return MainClass_List;
    }
    private static List<Plugin> MainClass_List =new ArrayList<>();
    public static List<Plugin> getChatEvent_List() {
        return ChatEvent_List;
    }
    private static List<Plugin> ChatEvent_List =new ArrayList<>();
    public static List<Plugin> getCommandEvent_List() {
        return CommandEvent_List;
    }
    private static List<Plugin> CommandEvent_List =new ArrayList<>();
    public static void AddMainClass(Plugin pl){
        MainClass_List.add(pl);
    }
    public static void AddChatEvent(Plugin pl){
        ChatEvent_List.add(pl);
    }
    public static void AddCommandEvent(Plugin pl){
        CommandEvent_List.add(pl);
    }
    public static URLClassLoader ucl=null;
    public static ConnectionPool.PooledConnection pool=null;
    public static RedisUtil ru=null;
    public static Set<Sockets> Sockets_Set=new HashSet<>();
}