package cn.edu.sysu.workflow.enginefeign.service;

/**
 * 系统定时任务服务
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ApplicationPerformanceMonitorService {

    /**
     * Activity after application starting
     */
    void postConstruct();

    /**
     * Activity before application exiting
     */
    void preDestroy();

    /**
     * Update engine information per 10 seconds.
     */
    void monitorRunner();
}
