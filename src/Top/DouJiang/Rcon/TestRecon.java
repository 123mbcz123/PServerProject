package Top.DouJiang.Rcon;

import Top.DouJiang.Command.ConsoleCommand;
import Top.DouJiang.Config.ConfigResult;

/**
 * Created by NicoNicoNi on 2017/8/13 0013.
 */
public class TestRecon {
    public static void main(String[] args){
        ConfigResult.isRunning=true;
        new Thread(new RconServer()).start();
    }
}
