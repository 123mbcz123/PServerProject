package Top.DouJiang.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class TestMain {
    public static void main(String[] args){
        ExecutorService es= Executors.newCachedThreadPool();
        new TestThread().start();
        for(int i=0;i<10000;i++) {
            es.execute(new TestSocket(String.valueOf(i),"753159"+i));
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                //
            }
        }
    }
}
