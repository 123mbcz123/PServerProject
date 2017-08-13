package Top.DouJiang.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class ConfigResult {
    public static int PrintLevel = 0;
    public static boolean isRunning = false;
    public static int ServerPort = -1;
    public static String Mysqls_host = null;
    public static String Mysqls_DB = null;
    public static String Mysqls_User = null;
    public static String Mysqls_Pass = null;
    public static int Mysqls_Port = -1;
    public static boolean TrueUseZipOrFalseUseGzip = false;
    public static boolean isUseZip = false;
    public static int RedisPort=-1;
    public static String RedisHost=null;
    public static String RconKey=null;
    public static String RconHost=null;
    public static int RconPort=-1;
    public static boolean isUseIpWhiteList=false;
    public static List<String> WhiteList=new ArrayList<>();
    /*
    0 最低
    1 最高
     */
}
