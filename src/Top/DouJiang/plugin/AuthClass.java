package Top.DouJiang.plugin;


import Top.DouJiang.ServerSocket.Sockets;

/**
 * Created by NicoNicoNi on 2017/8/9 0009.
 */
public class AuthClass {
    public Sockets getS() {
        return s;
    }

    private Sockets s=null;
    public AuthClass(Sockets s){
        this.s=s;
    }
}
