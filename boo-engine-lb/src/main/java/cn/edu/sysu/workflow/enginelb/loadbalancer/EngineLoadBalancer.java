package cn.edu.sysu.workflow.enginelb.loadbalancer;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.enginelb.dao.ServiceInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Skye
 * Created on 2020/6/9
 */

@Service
public class EngineLoadBalancer {

    private static final Logger log = LoggerFactory.getLogger(EngineLoadBalancer.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    /**
     * 轮询计数器
     */
    private int rrCount = 0;

    public BooReturnForm randomChoose(String path, MultiValueMap<String, String> params) {
        return restTemplate.postForObject(serviceInfoDAO.findEngineServiceUrlByRandom() + path, params, BooReturnForm.class);
    }

    public BooReturnForm roundRobinChoose(String path, MultiValueMap<String, String> params) {
        int index = this.rrCount;
        this.rrCount = (this.rrCount + 1) % serviceInfoDAO.findEngineServiceQuantity();
        return restTemplate.postForObject(serviceInfoDAO.findEngineServiceUrlByRoundRobin(index) + path, params, BooReturnForm.class);
    }

    public BooReturnForm assignationChoose(String path, MultiValueMap<String, String> params) {
        if (params.containsKey("processInstanceId")) {
            return restTemplate.postForObject(serviceInfoDAO.findEngineServiceUrlByProcessInstanceId(
                    params.get("processInstanceId").get(0)) + path, params, BooReturnForm.class);
        } else {
            log.error("processInstanceId does not exist.");
            throw new ServiceFailureException("processInstanceId does not exist.");
        }
    }

}
