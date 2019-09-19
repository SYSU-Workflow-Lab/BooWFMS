package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooServiceInfo extends BooPagedQuery {

    private static final long serialVersionUID = -2820224484319671126L;

    private String interpreterId;
    private String location;
    private int isActive;
    private double business;
    private double cpuOccupancyRate;
    private double memoryOccupancyRate;
    private double tomcatConcurrency;
    private double workitemCount;

    public BooServiceInfo() {
        this.isActive = 1;
    }

    public String getInterpreterId() {
        return interpreterId;
    }

    public void setInterpreterId(String interpreterId) {
        this.interpreterId = interpreterId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public double getWorkitemCount() {
        return workitemCount;
    }

    public void setWorkitemCount(double workitemCount) {
        this.workitemCount = workitemCount;
    }


}
