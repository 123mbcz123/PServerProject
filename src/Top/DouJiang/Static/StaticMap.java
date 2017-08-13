package Top.DouJiang.Static;

import Top.DouJiang.ServerSocket.ServerSockets;
import Top.DouJiang.ServerSocket.Sockets;
import Top.DouJiang.Util.Mysqls.ConnectionPool;
import Top.DouJiang.Util.Redis.RedisUtil;
import Top.DouJiang.plugin.Plugin;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class StaticMap {
    public static URLClassLoader ucl = null;
    public static ConnectionPool.PooledConnection pool = null;
    public static RedisUtil ru = null;
    public static Set<Sockets> Sockets_Set = new HashSet<>();
    public static int n = 0;
    public static ServerSockets ss = null;
    private static List<Plugin> MainClass_List = new ArrayList<>();
    private static List<Plugin> ChatEvent_List = new ArrayList<>();
    private static List<Plugin> CommandEvent_List = new ArrayList<>();
    private static List<Plugin> AuthEvent_List = new ArrayList<>();
    private static List<Plugin> JoinEvent_List = new ArrayList<>();
    private static List<Plugin> LeaveEvent_List = new ArrayList<>();
    private static List<Plugin> RegisterEvent_List = new ArrayList<>();
    private static List<Plugin> ConsoleCommandEvent_List = new ArrayList<>();
    public static List<Plugin> getAuthEvent_List() {
        return AuthEvent_List;
    }
    public static List<Plugin> getConsoleCommandEvent_List() {
        return ConsoleCommandEvent_List;
    }
    public static List<Plugin> getRegisterEvent_List () {
        return RegisterEvent_List ;
    }
    public static List<Plugin> getJoinEvent_List() {
        return JoinEvent_List;
    }
    public static List<Plugin> getLeaveEvent_List() {
        return LeaveEvent_List;
    }
    public static List<Plugin> getMainClass_List() {
        return MainClass_List;
    }
    public static List<Plugin> getChatEvent_List() {
        return ChatEvent_List;
    }
    public static List<Plugin> getCommandEvent_List() {
        return CommandEvent_List;
    }

    public static void setConsoleCommandEvent_List(List<Plugin> ConsoleCommandEvent_List){
        StaticMap.ConsoleCommandEvent_List=ConsoleCommandEvent_List;
    }

    public static void AddMainClass(Plugin pl) {
        MainClass_List.add(pl);
    }
    public static void AddChatEvent(Plugin pl) {
        ChatEvent_List.add(pl);
    }
    public static void AddCommandEvent(Plugin pl) {
        CommandEvent_List.add(pl);
    }
    public static void AddAuthEvent(Plugin pl) {
        AuthEvent_List.add(pl);
    }
    public static void AddJoinEvent(Plugin pl) {
        JoinEvent_List.add(pl);
    }
    public static void AddLeaveEvent(Plugin pl) {
        LeaveEvent_List.add(pl);
    }
    public static void AddRegisterEvent(Plugin pl) {
        RegisterEvent_List.add(pl);
    }
}
