package Top.DouJiang.Config;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class Config {
    /*
    报告详细度
    0 最详细
    1 为正常
    推荐为 1
    此项为必修改项
     */
    public int PrintLevel=-1;
    /*
    是否使用压缩
    占用U较高
     */
    public boolean isUseZip=true;
    /*
    True 为使用Zip
    False为使用Gzip
    Zip的效率貌似高一些
    上一项为false本项可以无视 为false true均不启用压缩
     */
    public boolean TrueUseZipOrFalseUseGzip=false;
    /*
    服务器占用端口
     */
    public int ServerPort=-1;
    /*
    Mysql 服务器IP
     */
    public  String Mysqls_host=null;
    /*
      Mysql 服务器Port
     */
    public  int Mysqls_Port=-1;
    /*
    Mysql 服务器数据库
     */
    public  String Mysqls_DB=null;
    /*
    Mysql 服务器账户名
     */
    public String Mysqls_User=null;
    /*
    Mysql 服务器密码
     */
    public  String Mysqls_Pass=null;
}
