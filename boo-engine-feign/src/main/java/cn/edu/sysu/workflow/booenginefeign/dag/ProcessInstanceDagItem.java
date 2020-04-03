package cn.edu.sysu.workflow.booenginefeign.dag;

/**
 * @author Skye
 * Created on 2020/4/3
 */
public class ProcessInstanceDagItem {

    private String taskItemId;

    private Double predictedStartTimestamp;

    private Double predictedFinishTimestamp;

    public String getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId;
    }

    public Double getPredictedStartTimestamp() {
        return predictedStartTimestamp;
    }

    public void setPredictedStartTimestamp(Double predictedStartTimestamp) {
        this.predictedStartTimestamp = predictedStartTimestamp;
    }

    public Double getPredictedFinishTimestamp() {
        return predictedFinishTimestamp;
    }

    public void setPredictedFinishTimestamp(Double predictedFinishTimestamp) {
        this.predictedFinishTimestamp = predictedFinishTimestamp;
    }
}
