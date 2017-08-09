package Top.DouJiang.Tool;

import Top.DouJiang.Config.ConfigReader;
import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Main.CallEventClass;
import Top.DouJiang.Main.ConsoleThread;
import Top.DouJiang.ServerSocket.ServerSockets;
import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.Util.Mysqls.ConnectionPool;
import Top.DouJiang.Util.Mysqls.DBManager;
import Top.DouJiang.Util.Redis.RedisUtil;
import Top.DouJiang.plugin.Plugin;
import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class SystemTools {
    /*
    初始化
      */
    public static void Initialize() {
        long startTime = System.nanoTime();   //获取开始时间
        Print("开始初始化............", 1, 0);
        ConfigResult.isRunning = true;
        ConfigReader.ReadConfig();
        CreateMysqlsConnection();
        CreateRedisPool();
        List<Plugin> pl = LoadPlugin();
        PluginCompositor(pl);
        Enable();
        CreateServerSocket();
        long endTime = System.nanoTime(); //获取结束时间
        Print("初始化完成............启动耗时: " + (endTime - startTime) + "ns", 1, 0);
        new ConsoleThread().start();
        new KeepThread().start();
    }

    /*
    创建ServerSocket
     */
    private static void CreateServerSocket() {
        ServerSockets ss = new ServerSockets();
        new Thread(ss).start();
    }

    /*
    创建Redis链接池
     */
    private static void CreateRedisPool() {
        RedisUtil ru = new RedisUtil("127.0.0.1", 1000);
        StaticMap.ru = ru;
    }

    /*
    关闭
     */
    public static void Close() {
        Print("服务器开始关闭.....", 1, 0);
        CallEventClass.onDisable();
        ConfigResult.isRunning = false;
        System.exit(0);
    }

    /*
    创建Mysql链接池
     */
    private static void CreateMysqlsConnection() {
        Print("开始创建Mysql链接............", 1, 0);
        ConnectionPool.PooledConnection pool = DBManager.getConnection(ConfigResult.Mysqls_host, ConfigResult.Mysqls_Port, ConfigResult.Mysqls_DB, ConfigResult.Mysqls_User, ConfigResult.Mysqls_Pass);
        if (pool == null) {
            Print("Mysql链接创建失败!服务器即将关闭!", 2, 0);
            Close();
        }
        StaticMap.pool = pool;
        Print("Mysql链接创建完成............", 1, 0);
    }

    /*
    加载插件
     */
    private static List<Plugin> LoadPlugin() {
        List<Plugin> Plugin_List = new ArrayList<>();
        File f = new File("Plugin//");
        if (!f.exists()) {
            f.mkdir();
        }
        File[] files = f.listFiles();
        Print("开始加载插件............", 1, 0);
        URL[] u = new URL[files.length];
        // List<Plugin> MainClass_List=new ArrayList<>();
        URLClassLoader ucl = null;
        for (int i = 0; i < files.length; i++) {
            File fi = files[i];
            Print("开始读取" + fi.getName() + "插件", 1, 0);
            File f1 = new File(fi.toPath() + "\\Plugin.yml");
            File f2 = new File(fi.toPath() + "\\core.jar");
            try {
                u[i] = new URL("file:" + f2);
                if (!f1.exists() || !f2.exists()) {
                    System.out.println(f1 + " " + f2);
                    Print("插件异常...文件不存在...错误代码0X001", 2, 0);
                    continue;
                }
                Plugin p = Yaml.loadType(f1, Plugin.class);
                if (p == null) {
                    Print("插件异常...Yml文件错误...错误代码0X010", 2, 0);
                    continue;
                }
                Plugin_List.add(p);//添加到临时队列
                // System.out.println("PM=="+p.MainClass+" PV=="+p.Verision+" PN=="+p.PluginName);
                //StaticMap.AddPlugin(p);
                Print("插件 " + fi.getName() + " 读取完成,版本号: " + p.Version, 1, 0);
            } catch (MalformedURLException e) {
                Print("插件异常...文件不存在...错误代码0X003", 2, 0);
            } catch (FileNotFoundException e) {
                Print("插件异常...Plugin.yml读取错误...错误代码0X002", 2, 0);
            }
        }
        ucl = new URLClassLoader(u);
        StaticMap.ucl = ucl;
        return Plugin_List;
    }

    /*
    根据插件设置优先级排序
     */
    /*/
    排序顺序:
    0 主类
    1 聊天
    2 指令
    3 登入
    4 加入
    5 离开
     */
    private static void  PluginCompositor(List<Plugin> Plugin_List){
        for(int i=0;i<6;i++){
            List<Plugin> Task_List = new ArrayList<>();
            Task_List.addAll(Plugin_List);
            //Print("正在对插件[Main]进行排序............", 1, 0);
            int MaxInt = 0;
            Plugin MaxPlugin = null;
            List<Plugin> Remove_List = new ArrayList<>();
            for (Plugin pl3 : Task_List) {
                switch (i) {
                    case 0:
                    if (pl3.MainClass == null) {
                        Remove_List.add(pl3);
                    }
                    break;
                    case 1:
                        if(pl3.ChatClass==null){
                            Remove_List.add(pl3);
                        }
                        break;
                    case 2:
                        if(pl3.CommandClass==null){
                            Remove_List.add(pl3);
                        }
                        break;
                    case 3:
                        if(pl3.AuthClass==null){
                            Remove_List.add(pl3);
                        }
                        break;
                    case 4:
                        if(pl3.JoinClass==null){
                            Remove_List.add(pl3);
                        }
                        break;
                    case 5:
                        if(pl3.LeaveClass==null){
                            Remove_List.add(pl3);
                        }
                        break;
                }
            }
            Task_List.removeAll(Remove_List);
            int i2 = Task_List.size();
            for (int m = 0; m < i2; m++) {
                for (int n = 0; n <  Task_List.size(); n++) {
                    Plugin pl2 = Task_List.get(n);
                    int num = 0;
                    switch (i){
                        case 0:
                            num=pl2.Main_Weight;
                            break;
                        case 1:
                            num=pl2.Chat_Weight;
                            break;
                        case 2:
                            num=pl2.Command_Weight;
                            break;
                        case 3:
                            num=pl2.Auth_Weight;
                            break;
                        case 4:
                            num=pl2.Join_Weight;
                            break;
                        case 5:
                            num=pl2.Leave_Weight;
                            break;
                    }
                    if (num >= MaxInt) {
                        MaxInt = num;
                        MaxPlugin = pl2;
                    }
                }
                if (MaxPlugin != null) {
                    switch (i){
                        case 0:
                            StaticMap.AddMainClass(MaxPlugin);
                            break;
                        case 1:
                            StaticMap.AddChatEvent(MaxPlugin);
                            break;
                        case 2:
                            StaticMap.AddCommandEvent(MaxPlugin);
                            break;
                        case 3:
                            StaticMap.AddAuthEvent(MaxPlugin);
                            break;
                        case 4:
                            StaticMap.AddJoinEvent(MaxPlugin);
                            break;
                        case 5:
                            StaticMap.AddLeaveEvent(MaxPlugin);
                            break;
                    }
                    Task_List.remove(MaxPlugin);
                }
                MaxInt = 0;
                MaxPlugin = null;
            }
            /*
            StringBuilder sb = new StringBuilder();
            if (!Task_List.isEmpty()) {
                for (Plugin p3 : StaticMap.getChatEvent_List()) {
                    sb.append(p3.PluginName);
                    sb.append(" > ");
                }
            }
            Print("[Chat]排序结果[从大到小]: " + sb.toString(), 1, 0);
            */

        }

    }
    /*
    private static void PluginCompositor(List<Plugin> Plugin_List) {
        List<Plugin> Main_List = new ArrayList<>();
        Main_List.addAll(Plugin_List);
        Print("正在对插件[Main]进行排序............", 1, 0);
        int MaxInt = 0;
        Plugin MaxPlugin = null;
        int i4 = Main_List.size();
        for (int m = 0; m < i4; m++) {
            for (int n = 0; n < Main_List.size(); n++) {
                Plugin pl2 = Main_List.get(n);
                int num = pl2.Main_Weight;
                if (num >= MaxInt) {
                    MaxInt = num;
                    MaxPlugin = pl2;
                }
            }
            StaticMap.AddMainClass(MaxPlugin);
            Main_List.remove(MaxPlugin);
            MaxInt = 0;
            MaxPlugin = null;
        }
        StringBuilder sb1 = new StringBuilder();
        for (Plugin p3 : StaticMap.getMainClass_List()) {
            sb1.append(p3.PluginName);
            sb1.append(" > ");
        }
        Print("[Main]排序结果[从大到小]: " + sb1.toString(), 1, 0);
        Print("正在对插件[Chat]进行排序............", 1, 0);
        List<Plugin> Chat_List = new ArrayList<>();
        List<Plugin> Remove_List = new ArrayList<>();
        Chat_List.addAll(Plugin_List);
        for (Plugin pl3 : Chat_List) {
            if (pl3.ChatClass == null) {
                Remove_List.add(pl3);
            }
        }
        Chat_List.removeAll(Remove_List);
        Remove_List.clear();
        for (int m = 0; m < i4; m++) {
            for (int n = 0; n < Chat_List.size(); n++) {
                Plugin pl2 = Chat_List.get(n);
                int num = pl2.Chat_Weight;
                if (num >= MaxInt) {
                    MaxInt = num;
                    MaxPlugin = pl2;
                }
            }
            if (MaxPlugin != null) {
                StaticMap.AddChatEvent(MaxPlugin);
                Chat_List.remove(MaxPlugin);
            }
            MaxInt = 0;
            MaxPlugin = null;
        }
        StringBuilder sb2 = new StringBuilder();
        if (!StaticMap.getChatEvent_List().isEmpty()) {
            System.out.println(StaticMap.getChatEvent_List());
            System.out.println(StaticMap.getChatEvent_List().isEmpty());
            for (Plugin p3 : StaticMap.getChatEvent_List()) {
                sb2.append(p3.PluginName);
                sb2.append(" > ");
            }
        }
        Print("[Chat]排序结果[从大到小]: " + sb2.toString(), 1, 0);
        Print("正在对插件[Command]进行排序............", 1, 0);
        List<Plugin> Command_List = new ArrayList<>();
        Chat_List.addAll(Plugin_List);
        for (Plugin pl3 : Command_List) {
            if (pl3.CommandClass == null) {
                Remove_List.add(pl3);
            }
        }
        Command_List.removeAll(Remove_List);
        Remove_List.clear();
        for (int m = 0; m < i4; m++) {
            for (int n = 0; n < Command_List.size(); n++) {
                Plugin pl2 = Command_List.get(n);
                int num = pl2.Command_Weight;
                if (num >= MaxInt) {
                    MaxInt = num;
                    MaxPlugin = pl2;
                }
            }
            if (MaxPlugin != null) {
                StaticMap.AddCommandEvent(MaxPlugin);
                Chat_List.remove(MaxPlugin);
            }
            MaxInt = 0;
            MaxPlugin = null;
        }
        StringBuilder sb3 = new StringBuilder();
        if (!StaticMap.getCommandEvent_List().isEmpty()) {
            for (Plugin p3 : StaticMap.getCommandEvent_List()) {
                sb3.append(p3.PluginName);
                sb3.append(" > ");
            }
        }
        Print("[Command]排序结果[从大到小]: " + sb3.toString(), 1, 0);

        Print("对插件排序进行完成............", 1, 0);
    }
    */
    /*
    启动完成时执行插件的onEnable方法
     */
    private static void Enable() {
        List<Plugin> Plugin_List = StaticMap.getMainClass_List();
        CallEventClass cec = new CallEventClass();
        cec.onEnable();
    }


    /**
     * 打印输出Log
     * 待保存
     *
     * @param str 信息
     * @param i   1信息 2 警告
     * @param l   权重 1位可忽略 2为重要
     */
    public static void Print(String str, int i, int l) {
        if (l <= ConfigResult.PrintLevel) {
            Date d = new Date();
            switch (i) {
                case 1:
                    System.out.println("[信息 " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "]" + str);
                    break;
                case 2:
                    System.out.println("[警告 " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "]" + str);
                    break;
            }
        }
    }
}
