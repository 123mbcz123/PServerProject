package Top.DouJiang.Test;

import Top.DouJiang.Tool.SocketTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class TestMain {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("输入你要使用的账号 0-99");
        String st=s.nextLine();
           TestSocket ts=new TestSocket(st, "753159" + st);
           ts.start();
           while (true){
               System.out.println("输入你要发送的群");
               String GroupId=s.nextLine();
               System.out.println("输入你要发送消息");
               String str=s.nextLine();
               Map<String,String> ChatMap=new HashMap<>();
               ChatMap.put("Type","2");
               ChatMap.put("Cmd","Chat");
               ChatMap.put("Msg", SocketTools.Base64Encryption(str));
               ChatMap.put("ToId",GroupId);
               ts.Send(ChatMap);
           }
    }
}
