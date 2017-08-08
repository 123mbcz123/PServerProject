package Top.DouJiang.Config;

import Top.DouJiang.Tool.SystemTools;
import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public class ConfigReader {
    public static void ReadConfig(){
        SystemTools.Print("开始加载Config.yml..............",1,0);
        File f=new File("Config.yml");
        if(!f.exists()){
            Config c=new Config();
            c.PrintLevel=0;
            try {
                Yaml.dump(c,f);
                SystemTools.Print("首次初始化,请设置配置文件,服务器关闭中...",1,0);
                System.exit(0);//强制关闭
            } catch (IOException e) {
                SystemTools.Print("创建Config.yml失败,服务器关闭!",2,0);
                SystemTools.Close();

            }
        }
        try {
            Config c=Yaml.loadType(f,Config.class);
            if(c==null){
                SystemTools.Print("读取Config.yml失败,服务器关闭!",2,0);
                SystemTools.Close();
            }
            ConfigResult.PrintLevel=c.PrintLevel;
        } catch (FileNotFoundException e) {
            SystemTools.Print("Config.yml不存在,服务器关闭!",2,0);
            SystemTools.Close();
        }
        SystemTools.Print("Config.yml加载完成..............",1,0);
    }
}
