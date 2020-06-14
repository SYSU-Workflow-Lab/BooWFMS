package cn.edu.sysu.workflow.activiti.admission.timewheel;

/**
 * 需要延迟执行的任务，放在Bucket里
 * @author: Gordan Lin
 * @create: 2019/12/12
 **/
public class TimerTask {

    // 延迟时间
    private long delayMs;

    // 任务
    private Runnable task;

    public TimerTask(long delayMs, Runnable task) {
        this.delayMs = delayMs;
        this.task = task;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public Runnable getTask() {
        return task;
    }
}
