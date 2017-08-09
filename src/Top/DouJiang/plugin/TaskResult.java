package Top.DouJiang.plugin;

/**
 * Created by NicoNicoNi on 2017/8/5 0005.
 */
public class TaskResult {
    private boolean is_Cancel = false;//是否取消任务

    public boolean isCancel() {
        return is_Cancel;
    }

    public void SetCancel(boolean b) {
        is_Cancel = b;
    }
}
