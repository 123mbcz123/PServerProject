package Top.DouJiang.plugin;


/**
 * Created by NicoNicoNi on 2017/8/4 0004.
 */
public interface PluginMain {
    /*
    此类用于启动时执行
     */

    /*
     * 启动时加载
     */
    void onEnable();

    /*
    结束时加载
     */
    void onDisable();
}
