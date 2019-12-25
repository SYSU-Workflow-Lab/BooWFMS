package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

/**
 * Service Info of BooWFMS.
 *
 * Created by Skye on 2019/12/18.
 */
public class ServiceInfo extends BooPagedQuery {

    private static final long serialVersionUID = -2820224484319671126L;

    /**
     * 服务信息ID
     */
    private String serviceInfoId;

    /**
     * 服务URL
     */
    private String url;

    /**
     * 服务是否存活
     */
    private int isActive;

    /**
     * 繁忙程度性能指标
     */
    private double business;

    /**
     * CPU占用率
     */
    private double cpuOccupancyRate;

    /**
     * 内存占用率
     */
    private double memoryOccupancyRate;

    /**
     * tomcat连接并发数
     */
    private double tomcatConcurrency;

    /**
     * 工作项个数
     */
    private double workItemCount;

    public ServiceInfo(String serviceInfoId) {
        this.serviceInfoId = serviceInfoId;
        this.isActive = 1;
    }

    public String getServiceInfo() {
        return serviceInfoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public double getBusiness() {
        return business;
    }

    public void setBusiness(double business) {
        this.business = business;
    }

    public double getCpuOccupancyRate() {
        return cpuOccupancyRate;
    }

    public void setCpuOccupancyRate(double cpuOccupancyRate) {
        this.cpuOccupancyRate = cpuOccupancyRate;
    }

    public double getMemoryOccupancyRate() {
        return memoryOccupancyRate;
    }

    public void setMemoryOccupancyRate(double memoryOccupancyRate) {
        this.memoryOccupancyRate = memoryOccupancyRate;
    }

    public double getTomcatConcurrency() {
        return tomcatConcurrency;
    }

    public void setTomcatConcurrency(double tomcatConcurrency) {
        this.tomcatConcurrency = tomcatConcurrency;
    }

    public double getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(double workItemCount) {
        this.workItemCount = workItemCount;
    }
}
