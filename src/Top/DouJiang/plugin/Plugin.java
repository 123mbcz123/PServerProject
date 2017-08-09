package Top.DouJiang.plugin;

import java.io.Serializable;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class Plugin implements Serializable {
    public String MainClass = null; //主类的位置 不可为null
    public String ChatClass = null;//聊天事件的位置 可为null
    public int Chat_Weight = 0; //聊天事件的权重
    public int Command_Weight = 0;//指令事件的权重
    public String CommandClass = null;//指令事件的位置 可为null
    public String PluginName = null;//插件名字 不可为null
    public String Version = null;//插件版本 不可为null
    public int Main_Weight = 0;//主类的权重
    public int Auth_Weight =0;//登入的权重
    public String AuthClass=null;//登入事件的位置
    public int Join_Weight=0;//加入事件的权重
    public String JoinClass=null;//加入事件的权重
    public int Leave_Weight=0;//退出事件的权重
    public String LeaveClass=null;//退出事件的权重
}
