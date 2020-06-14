package cn.edu.sysu.workflow.activiti.admission.timewheel;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 定时器
 * @author: Gordan Lin
 * @create: 2019/12/12
 **/
public class Timer {

    private static Logger logger = LoggerFactory.getLogger(Timer.class);

    // 时间槽时间长度，单位是毫秒
    private static final int TICK_MS = 1000;
    // 时间槽个数
    private static final int WHEEL_SIZE = 60;
    // 滑动时间窗口大小
    private static final int TIME_WINDOW_SIZE = 20;

    // 时间轮
    private TimingWheel timingWheel;

    // 对于一个Timer以及附属的时间轮，都只有一个priorityQueue
    private PriorityBlockingQueue<Bucket> priorityQueue = new PriorityBlockingQueue<>(WHEEL_SIZE+1, new Comparator<Bucket>() {
        @Override
        public int compare(Bucket bucket1, Bucket bucket2) {
            return (int) (bucket1.getExpire()-bucket2.getExpire());
        }
    });

    // 优先队列中各个bucket任务数，通过TIME_WINDOW_SIZE控制长度实现滑动时间窗口（空间换时间）
    private LinkedList<Integer> timeWindow = new LinkedList<>();

    private ExecutorService workerThreadPool;

    private ScheduledExecutorService bossThreadPool;

    private static Timer TIMER_INSTANCE;

    public static Timer getInstance() {
        if (TIMER_INSTANCE == null) {
            synchronized (Timer.class) {
                if (TIMER_INSTANCE == null) {
                    TIMER_INSTANCE = new Timer();
                }
            }
        }
        return TIMER_INSTANCE;
    }

    private Timer() {
        workerThreadPool = Executors.newFixedThreadPool(100, new ThreadFactoryBuilder().setPriority(10)
                .setNameFormat("TimerWheelWorker")
                .build());
        bossThreadPool = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder().setPriority(10)
                .setNameFormat("TimerWheelBoss")
                .build());

        timingWheel = new TimingWheel(TICK_MS, WHEEL_SIZE, System.currentTimeMillis(), priorityQueue);
        bossThreadPool.scheduleAtFixedRate(() -> {
            TIMER_INSTANCE.advanceClock();
        }, 0, TICK_MS, TimeUnit.MILLISECONDS);
    }

    public LinkedList<Integer> getTimeWindow() {
        return timeWindow;
    }

    /**
     * 将任务添加到时间轮
     */
    public void addTask(TimerTask timerTask) {
        if (!timingWheel.addTask(timerTask)) {
            workerThreadPool.submit(timerTask.getTask());
        }
    }

    /**
     *  指针推进
     */
    public void advanceClock() {
        long currentTimestamp = System.currentTimeMillis();
        timingWheel.advanceClock(currentTimestamp);

        Bucket bucket = priorityQueue.peek();
        if (bucket == null || bucket.getExpire() > currentTimestamp) {
//            logger.info("当前负载：0");
            return;
        }

        try {
            // 执行请求
            List<TimerTask> taskList = admitting();
//            logger.info("当前负载：{}", taskList.size());
            if (!timeWindow.isEmpty()) timeWindow.removeFirst();

            for (TimerTask timerTask : taskList) {
                workerThreadPool.submit(timerTask.getTask());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * admitting算法
     * @return
     */
    private List<TimerTask> admitting() {
        List<TimerTask> taskList = null;

        int requestSum = 0; // 滑动窗口请求总数
        int requestAvg = 0; // 滑动窗口请求平均数
        int i;
        for (i = 1; i <= timeWindow.size(); i++) {
            if (i > TIME_WINDOW_SIZE) break;
            requestSum += timeWindow.get(i-1);
        }
        requestAvg = requestSum/i;

        if (timeWindow.isEmpty() || timeWindow.get(0) > requestAvg) {
            Bucket bucket = priorityQueue.poll();
            taskList = bucket.removeTaskAndGet(-1);
        } else {
            Bucket bucket = priorityQueue.poll();
            taskList = bucket.removeTaskAndGet(-1);

            int moveCount = requestAvg-timeWindow.get(0);
            // 移动时间槽请求
            if (moveCount > 0) {
                Bucket tempBucket = priorityQueue.peek();
                List<TimerTask> tempTaskList = tempBucket.removeTaskAndGet(moveCount);
                taskList.addAll(tempTaskList);
                int remain = timeWindow.get(1)-tempTaskList.size();
                timeWindow.set(1, remain);
            }
        }
        return taskList;
    }

}
