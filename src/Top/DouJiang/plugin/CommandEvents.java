package Top.DouJiang.plugin;

/**
 * Created by NicoNicoNi on 2017/8/7 0007.
 */
public interface CommandEvents {
    /*
    执行CMD指令时加载此类
     */
    CommandResult CommandEvent(CommandClass cc);
}
