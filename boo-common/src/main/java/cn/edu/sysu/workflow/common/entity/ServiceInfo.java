package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.sql.Timestamp;

/**
 * Service Info of BooWFMS.
 *
 * @author Skye
 * Created on 2019/12/18
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
    private Boolean isActive;

    /**
     * 繁忙程度性能指标
     */
    private Double business;

    /**
     * CPU占用率
     */
    private Double cpuOccupancyRate;

    /**
     * 内存占用率
     */
    private Double memoryOccupancyRate;

    /**
     * Tomcat连接并发数
     */
    private Double tomcatConcurrency;

    /**
     * 工作项个数
     */
    private Double workItemCount;

    public ServiceInfo() {
        super();
        this.isActive = true;
        this.business = 0.0;
        this.cpuOccupancyRate = 0.0;
        this.memoryOccupancyRate = 0.0;
        this.tomcatConcurrency = 0.0;
        this.workItemCount = 0.0;
    }

    public String getServiceInfoId() {
        return serviceInfoId;
    }

    public void setServiceInfoId(String serviceInfoId) {
        this.serviceInfoId = serviceInfoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Double getBusiness() {
        return business;
    }

    public void setBusiness(Double business) {
        this.business = business;
    }

    public Double getCpuOccupancyRate() {
        return cpuOccupancyRate;
    }

    public void setCpuOccupancyRate(Double cpuOccupancyRate) {
        this.cpuOccupancyRate = cpuOccupancyRate;
    }

    public Double getMemoryOccupancyRate() {
        return memoryOccupancyRate;
    }

    public void setMemoryOccupancyRate(Double memoryOccupancyRate) {
        this.memoryOccupancyRate = memoryOccupancyRate;
    }

    public Double getTomcatConcurrency() {
        return tomcatConcurrency;
    }

    public void setTomcatConcurrency(Double tomcatConcurrency) {
        this.tomcatConcurrency = tomcatConcurrency;
    }

    public Double getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(Double workItemCount) {
        this.workItemCount = workItemCount;
    }

    /**
     * 更新繁忙程度指标
     */
    public void updateBusiness() {
        this.business = cpuOccupancyRate + memoryOccupancyRate + tomcatConcurrency + workItemCount / 10000.0;
    }
}
