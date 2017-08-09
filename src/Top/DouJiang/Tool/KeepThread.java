package Top.DouJiang.Tool;

import Top.DouJiang.Config.ConfigResult;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class KeepThread extends Thread {
    public void run() {
        while (ConfigResult.isRunning) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
