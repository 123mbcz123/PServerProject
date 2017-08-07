package Top.DouJiang.plugin;

import Top.DouJiang.ServerSocket.Sockets;

import java.net.Socket;

/**
 * Created by NicoNicoNi on 2017/8/5 0005.
 */
public class TaskClass {

    /*
        Task下发类
        所有数据通过传送此类来实现
        为了方便理解用QQ来举例
         */
    public String getFromId() {
        return FromId;
    }
    private String FromId=null;//来自的QQ账号
    public String getToId() {
        return ToId;
    }
    private String ToId=null;//发送到的QQ账号/群
    public String getMsg() {
        return Msg;
    }
    private String Msg=null;//聊天的内容
    private String TaskId=null;//信息的ID 暂时保留
    public int getType() {
        return Type;
    }
    private int Type=-1; //1为QQ群,2为私聊
    public Sockets getS() {
        return s;
    }
    private Sockets s=null;

    public TaskClass(String FromId,String ToId,int Type ,String Msg){
        this.FromId=FromId;
        this.Msg=Msg;
        this.ToId=ToId;
        this.Type=Type;
    }
    public TaskClass(String FromId,String ToId,int Type ,String Msg,Sockets s){
        this.FromId=FromId;
        this.Msg=Msg;
        this.ToId=ToId;
        this.Type=Type;
        this.s=s;
    }
}
