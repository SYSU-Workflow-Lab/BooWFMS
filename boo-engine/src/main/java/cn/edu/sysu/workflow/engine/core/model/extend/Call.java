package cn.edu.sysu.workflow.engine.core.model.extend;

import cn.edu.sysu.workflow.common.constant.LocationContext;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.ActionExecutionContext;
import cn.edu.sysu.workflow.engine.core.BOXMLExecutionContext;
import cn.edu.sysu.workflow.engine.core.Context;
import cn.edu.sysu.workflow.engine.core.SCXMLExpressionException;
import cn.edu.sysu.workflow.engine.core.model.EnterableState;
import cn.edu.sysu.workflow.engine.core.model.ModelException;
import cn.edu.sysu.workflow.engine.core.model.Param;
import cn.edu.sysu.workflow.engine.core.model.ParamsContainer;
import cn.edu.sysu.workflow.engine.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Rinkako
 * Date  : 2017/3/8
 * Usage : Label context of Call.
 */
public class Call extends ParamsContainer implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static final Logger log = LoggerFactory.getLogger(Call.class);

    /**
     * The name of the task or the sub process to call
     */
    private String name;

    /**
     * How many task instances ought to be create in expression.
     */
    private String instanceExpr = "1";

    /**
     * Get the value of name
     *
     * @return the task name to call
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name the task name to call
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of instance
     *
     * @return value of instance property
     */
    public String getInstances() {
        return this.instanceExpr;
    }

    /**
     * Set the value of instance
     *
     * @param instances the instance value to set, represent how many task instances ought to be created
     */
    public void setInstances(String instances) {
        this.instanceExpr = instances;
    }

    /**
     * Execute Call, send request to resource service.
     *
     * @param exctx The ActionExecutionContext for this execution instance
     * @throws ModelException
     * @throws SCXMLExpressionException
     */
    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {
        if (BooEngineApplication.IS_LOCAL_DEBUG) {
            log.info(">>> CALL: " + this.name);
        }
        BOXMLExecutionContext scxmlExecContext = (BOXMLExecutionContext) exctx.getInternalIOProcessor();
        EnterableState parentState = getParentEnterableState();
        Context ctx = exctx.getContext(parentState);
        ctx.setLocal(getNamespacesKey(), getNamespaces());
        Map<String, Object> payloadDataMap = new LinkedHashMap();
        addParamsToPayload(exctx, payloadDataMap);
        Tasks tasks = scxmlExecContext.getSCXMLExecutor().getStateMachine().getTasks();
        if (tasks != null) {
            List<Task> taskList = tasks.getTaskList();
            List<SubProcess> processList = tasks.getProcessList();
            boolean successFlag = false;
            if (!taskList.isEmpty()) {
                for (Task t : taskList) {
                    if (t.getName().equals(this.name)) {
                        // generate arguments json
                        StringBuilder sb = new StringBuilder();
                        sb.append("{");
                        for (Param p : this.getParams()) {
                            try {
                                sb.append(p.GenerateDescriptor(scxmlExecContext.getEvaluator(), ctx)).append(",");
                            } catch (Exception ex) {
                                log.error("[" + scxmlExecContext.processInstanceId + "]" + ex.toString());
                            }
                        }
                        String jsonifyParam = sb.toString();
                        if (jsonifyParam.length() > 1) {
                            jsonifyParam = jsonifyParam.substring(0, jsonifyParam.length() - 1);
                        }
                        jsonifyParam += "}";
                        // send to RS
                        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
                        requestEntity.add("polymorphismName", this.name);
                        requestEntity.add("businessObjectName", scxmlExecContext.getSCXMLExecutor().getStateMachine().getName());
                        requestEntity.add("nodeId", scxmlExecContext.getSCXMLExecutor().NodeId);
                        requestEntity.add("args", jsonifyParam);
                        requestEntity.add("processInstanceId", scxmlExecContext.processInstanceId);
                        if (!BooEngineApplication.IS_LOCAL_DEBUG) {
                            int timesBorder = (int) scxmlExecContext.getEvaluator().eval(ctx, this.instanceExpr);
                            if ("Offer".equalsIgnoreCase(t.getPrinciple().getMethod())) {
                                if (timesBorder != 1) {
                                    log.error("[" + ((BOXMLExecutionContext) exctx.getInternalIOProcessor()).processInstanceId + "]" + "Method is Offer but instance not 1 is invalid, use default value 1.");
                                }
                                timesBorder = 1;
                            }
                            for (int times = 0; times < timesBorder; times++) {
                                try {
                                    RestTemplate restTemplate = (RestTemplate) SpringContextUtil.getBean("restTemplate");
                                    restTemplate.postForObject(LocationContext.RESOURCE + LocationContext.URL_RS_SUBMITTASK, requestEntity, String.class);
                                } catch (Exception e) {
                                    log.error("[" + scxmlExecContext.processInstanceId + "]When submit task to Resource Service, exception occurred, " + e.toString());
                                }
                            }
                        }
                        successFlag = true;
                        break;
                    }
                }
            } else if (!processList.isEmpty()) {
                for (SubProcess subProcess : processList) {
                    if (subProcess.getName().equals(this.name)) {
                        // generate arguments json
                        StringBuilder sb = new StringBuilder();
                        sb.append("{");
                        for (Param p : this.getParams()) {
                            try {
                                sb.append(p.GenerateDescriptor(scxmlExecContext.getEvaluator(), ctx)).append(",");
                            } catch (Exception ex) {
                                log.error("[" + scxmlExecContext.processInstanceId + "]" + ex.toString());
                            }
                        }
                        String jsonifyParam = sb.toString();
                        if (jsonifyParam.length() > 1) {
                            jsonifyParam = jsonifyParam.substring(0, jsonifyParam.length() - 1);
                        }
                        jsonifyParam += "}";
                        // send to RS
                        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
                        requestEntity.add("polymorphismName", this.name);
                        requestEntity.add("businessObjectName", scxmlExecContext.getSCXMLExecutor().getStateMachine().getName());
                        requestEntity.add("nodeId", scxmlExecContext.getSCXMLExecutor().NodeId);
                        //params of the task
                        requestEntity.add("args", jsonifyParam);
                        requestEntity.add("processInstanceId", scxmlExecContext.processInstanceId);
                        if (!BooEngineApplication.IS_LOCAL_DEBUG) {
                            try {
                                RestTemplate restTemplate = (RestTemplate) SpringContextUtil.getBean("restTemplate");
                                restTemplate.postForObject(LocationContext.RESOURCE + LocationContext.URL_RS_SUBMITTASK, requestEntity, String.class);
                            } catch (Exception e) {
                                log.error("[" + scxmlExecContext.processInstanceId + "]When submit task to Resource Service, exception occurred, " + e.toString());
                            }
                        }
                        successFlag = true;
                        break;
                    }
                }
            }
            if (!successFlag) {
                throw new ModelException();
            }
        } else {
            throw new ModelException();
        }
    }
}
