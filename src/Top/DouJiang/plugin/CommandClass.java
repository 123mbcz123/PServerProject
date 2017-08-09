package Top.DouJiang.plugin;

import Top.DouJiang.ServerSocket.Sockets;

import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/6 0006.
 */
public class CommandClass {
    private Map<String, String> CmdMap = null;
    private Sockets s = null;

    public CommandClass(Sockets s, Map<String, String> CmdMap) {
        this.CmdMap = CmdMap;
        this.s = s;
    }

    //用于获取指令任务
    public Map<String, String> getCmdMap() {
        return CmdMap;
    }

    //用于发送指令等操作
    public Sockets getS() {
        return s;
    }
}
