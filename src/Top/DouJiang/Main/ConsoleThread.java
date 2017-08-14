package Top.DouJiang.Main;

import Top.DouJiang.Command.ConsoleCommand;
import Top.DouJiang.Config.ConfigResult;
import Top.DouJiang.Tool.SystemTools;

import java.util.Scanner;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class ConsoleThread extends Thread {
    public void run() {
        Scanner sc = new Scanner(System.in);
        String str = null;
        while (ConfigResult.isRunning) {
            str = null;
            str = sc.nextLine();
            if (str == null) {
                SystemTools.Print("错误的指令输入help查看全部指令!", 2, 0);
                continue;
            }
            String[] strs = str.split("\\ ");
            if (strs.length== 0) {
                SystemTools.Print("错误的指令输入help查看全部指令!", 2, 0);
                continue;
            }
            ConsoleCommand cc=new ConsoleCommand(strs,1);
            cc.CallConsoleCommand();
            cc=null;
        }
    }
}
