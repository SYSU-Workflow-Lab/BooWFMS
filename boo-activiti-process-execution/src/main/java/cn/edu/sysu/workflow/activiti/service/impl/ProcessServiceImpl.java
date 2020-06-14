package cn.edu.sysu.workflow.activiti.service.impl;

import cn.edu.sysu.workflow.activiti.admission.limiter.SLALimit;
import cn.edu.sysu.workflow.activiti.admission.timewheel.ActivitiTask;
import cn.edu.sysu.workflow.activiti.admission.timewheel.Timer;
import cn.edu.sysu.workflow.activiti.admission.timewheel.TimerTask;
import cn.edu.sysu.workflow.activiti.util.CommonUtil;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import cn.edu.sysu.workflow.activiti.service.ProcessService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ProcessServiceImpl implements ProcessService, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(ProcessService.class);

    @Autowired
    RestTemplate restTemplate;

    private final String URL_PREFIX = "http://activiti-engine/activiti-engine";

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer.getInstance();
    }

    @Override
    public ResponseEntity<?> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables) {
        String url = URL_PREFIX + "/startProcessInstanceByKey/" + processModelKey;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> startProcessInstanceById(String processInstanceId, Map<String, Object> variables) {
        String url = URL_PREFIX + "/startProcessInstanceById/" + processInstanceId;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> getCurrentTasks(String processInstanceId) {
        String url = URL_PREFIX + "/getCurrentTasks/" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> getCurrentTasks(String processInstancedId, Map<String,Object> variables) {
        String url = URL_PREFIX + "/getCurrentTasksOfAssignee/" + processInstancedId;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class, valueMap);
        return result;
    }

    @Override
    public ResponseEntity<?> getCurrentSingleTask(String processInstanceId) {
        String url = URL_PREFIX + "/getCurrentSingleTask/" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> claimTask(String processInstanceId, String taskId, String assignee) {
        String url = URL_PREFIX+ "/claimTask/" + processInstanceId + "/" + taskId;
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", assignee);
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> isEnded(String processInstanceId) {
        String url = URL_PREFIX + "/isEnded/" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    // 不延迟
    @Override
    public ResponseEntity<?> completeTask(String taskId, String processDefinitionId, String processInstanceId, Map<String, Object> variables) {
        String url = URL_PREFIX + "/completeTask/" + processDefinitionId + "/" + processInstanceId + "/" + taskId;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }


    //延迟请求
    public ResponseEntity<?> completeTaskWithDelay(String taskId, String processDefinitionId, String processInstanceId, Map<String, Object> variables) {
        //获取租户SLA级别定义
        int rar = Integer.valueOf((String) variables.get("rar"));
        int rtl = (Integer) variables.get("rtl");

        RateLimiter limiter = null;
        try {
            // key要求：tenantId-rarLevel
            limiter = SLALimit.requestRateLimiterCaches.get("test-"+rar);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!limiter.tryAcquire()) {
            logger.error("请求由于限流被拒绝");
            return ResponseEntity.ok("请求由于限流被拒绝");
        }

        String url = URL_PREFIX + "/completeTask/" + processDefinitionId + "/" + processInstanceId + "/" + taskId;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(variables);
        ActivitiTask activitiTask = new ActivitiTask(url, valueMap, restTemplate);
        TimerTask timerTask = new TimerTask(rtl* SLALimit.RESPONSE_TIME_PER_LEVEL, activitiTask);
        Timer.getInstance().addTask(timerTask);

        return ResponseEntity.ok("请求正在调度中");
    }

}