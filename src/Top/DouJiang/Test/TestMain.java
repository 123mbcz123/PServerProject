package Top.DouJiang.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class TestMain {
    public static void main(String[] args){
        ExecutorService es= Executors.newCachedThreadPool();
        for(int i=0;i<100000;i++) {
            es.execute(new TestSocket(String.valueOf(i),"753159"+i));
        }
        System.out.println("执行完毕");

    }
}
