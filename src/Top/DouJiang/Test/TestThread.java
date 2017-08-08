package Top.DouJiang.Test;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class TestThread extends Thread {
    public static int all=0;
    public static int fail=0;
    public void run(){
        for(;;){
            System.out.println("ALL="+all+" Fail="+fail);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
