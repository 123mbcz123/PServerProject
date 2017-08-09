package Top.DouJiang.Main;

import Top.DouJiang.Static.StaticMap;
import Top.DouJiang.plugin.*;

import java.net.URLClassLoader;
import java.util.List;

import static Top.DouJiang.Tool.SystemTools.Print;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class CallEventClass {
    /*
    执行启动
     */
    public static void onEnable() {
        List<Plugin> Plugin_List = StaticMap.getMainClass_List();
        URLClassLoader ucl = StaticMap.ucl;
        Print("开始启动onEnable方法.......", 1, 1);
        for (Plugin pl : Plugin_List) {
            try {
                Print("开始执行" + pl.PluginName + "插件", 1, 1);
                Class<?> c = ucl.loadClass(pl.MainClass);
                Object obj = c.newInstance();
                // System.out.println("UCL="+ucl+" Obj="+obj);
                if (obj == null) {
                    Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X007", 2, 0);
                    continue;
                }
                if (!(obj instanceof PluginMain)) {
                    Print("插件异常[" + pl.PluginName + "]...引用接口错误...错误代码0X011", 2, 0);
                }
                PluginMain pm = (PluginMain) obj;
                pm.onEnable();
                Print("执行" + pl.PluginName + "插件完成!", 1, 1);
            } catch (ClassNotFoundException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X004", 2, 0);
            } catch (IllegalAccessException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X005", 2, 0);
            } catch (InstantiationException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X006", 2, 0);
            }
        }
        Print("onEnable方法执行完毕.......", 1, 1);
    }

    /*
    执行关闭
     */
    public static void onDisable() {
        List<Plugin> Plugin_List = StaticMap.getMainClass_List();
        URLClassLoader ucl = StaticMap.ucl;
        Print("开始启动onDisable方法.......", 1, 1);
        for (Plugin pl : Plugin_List) {
            try {
                Print("开始执行" + pl.PluginName + "插件", 1, 1);
                Class<?> c = ucl.loadClass(pl.MainClass);
                Object obj = c.newInstance();
                // System.out.println("UCL="+ucl+" Obj="+obj);
                if (obj == null) {
                    Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X007", 2, 0);
                    continue;
                }
                if (!(obj instanceof PluginMain)) {
                    Print("插件异常[" + pl.PluginName + "]...引用接口错误...错误代码0X011", 2, 0);
                }
                PluginMain pm = (PluginMain) obj;
                pm.onDisable();
                Print("执行" + pl.PluginName + "插件完成!", 1, 1);
            } catch (ClassNotFoundException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X004", 2, 0);
            } catch (IllegalAccessException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X005", 2, 0);
            } catch (InstantiationException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X006", 2, 0);
            }
        }
        Print("onDisable方法执行完毕.......", 1, 1);
    }

    /*
    执行指令
     */
    public void CallCommandRunner(CommandClass cc) {
        List<Plugin> Plugin_List = StaticMap.getMainClass_List();
        URLClassLoader ucl = StaticMap.ucl;
        Print("开始下发任务.......", 1, 1);
        for (Plugin pl : Plugin_List) {
            try {
                Print("开始执行" + pl.PluginName + "插件", 1, 1);
                Class<?> c = ucl.loadClass(pl.CommandClass);
                Object obj = c.newInstance();
                // System.out.println("UCL="+ucl+" Obj="+obj);
                if (obj == null) {
                    Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X007", 2, 0);
                    continue;
                }
                if (!(obj instanceof PluginMain)) {
                    Print("插件异常[" + pl.PluginName + "]...引用接口错误...错误代码0X011", 2, 0);
                }
                CommandEvents ce = (CommandEvents) obj;
                CommandResult cr = ce.CommandEvent(cc);
                if (cr != null) {
                    if (cr.isCancel()) {
                        //如果任务被取消 退出
                        Print("插件[" + pl.PluginName + "]取消了Command任务!", 1, 1);
                        return;
                    }
                }
                Print("执行" + pl.PluginName + "插件完成!", 1, 1);
            } catch (ClassNotFoundException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X004", 2, 0);
            } catch (IllegalAccessException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X005", 2, 0);
            } catch (InstantiationException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X006", 2, 0);
            }
        }
        Print("任务下发执行完毕.......", 1, 1);
    }

    /*
    执行聊天
     */
    public void CallChatEvent(TaskClass tc) {
        List<Plugin> Plugin_List = StaticMap.getChatEvent_List();
        URLClassLoader ucl = StaticMap.ucl;
        Print("开始下发任务.......", 1, 1);
        for (Plugin pl : Plugin_List) {
            try {
                Print("开始执行" + pl.PluginName + "插件", 1, 1);
                Class<?> c = ucl.loadClass(pl.MainClass);
                Object obj = c.newInstance();
                // System.out.println("UCL="+ucl+" Obj="+obj);
                if (obj == null) {
                    Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X007", 2, 0);
                    continue;
                }
                if (!(obj instanceof PluginMain)) {
                    Print("插件异常[" + pl.PluginName + "]...引用接口错误...错误代码0X011", 2, 0);
                }
                ChatEvents ce = (ChatEvents) obj;
                TaskResult tr = ce.ChatEvent(tc);
                if (tr != null && tr.isCancel()) {
                    return;
                }
                Print("执行" + pl.PluginName + "插件完成!", 1, 1);
            } catch (ClassNotFoundException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X004", 2, 0);
            } catch (IllegalAccessException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X005", 2, 0);
            } catch (InstantiationException e) {
                Print("插件异常[" + pl.PluginName + "]...执行类错误...错误代码0X006", 2, 0);
            }
        }
        Print("任务下发执行完毕.......", 1, 1);
    }
}
