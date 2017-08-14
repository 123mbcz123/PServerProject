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
           TestSocket ts=new TestSocket();
           ts.start();
    }
}
