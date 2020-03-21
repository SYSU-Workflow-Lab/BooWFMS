package cn.edu.sysu.workflow.resource.core.plugin;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.enums.AgentReentrantType;
import cn.edu.sysu.workflow.resource.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author: Rinkako
 * Date  : 2018/2/8
 * Usage : Plugin for acting as a Client to send HTTP request to agent URL.
 */
public class AgentNotifyPlugin extends AsyncRunnablePlugin {

    private static final Logger log = LoggerFactory.getLogger(AgentNotifyPlugin.class);

    /**
     * Message to be sent.
     */
    private ArrayList<NotifyMessagePackage> pendingMessage = new ArrayList<>();

    /**
     * Flag for sending.
     */
    private boolean isBeginSending = false;

    /**
     * Global static queue, in pattern (NotReentrantAgentGlobalId, NotificationPackageQueue).
     * NOTICE this is only a queue of current RS, other RS may send notification at the same time.
     */
    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<Object>> pendingQueue = new ConcurrentHashMap<>();

    private RestTemplate restTemplate;

    /**
     * Create a new agent notification send plugin.
     */
    public AgentNotifyPlugin() {
        restTemplate = (RestTemplate) SpringContextUtil.getBean("restTemplate");
    }

    /**
     * Run the plugin.
     * This method will be the asynchronously running entry point.
     */
    @Override
    public void run() {
        this.handlePendingMessage();
    }

    /**
     * Add a notification to post.
     *
     * @param agent             agent participant
     * @param args              arguments to post
     * @param processInstanceId process instance id
     */
    public void addNotification(ProcessParticipant agent, Map<String, String> args, String processInstanceId) {
        if (!this.isBeginSending) {
            NotifyMessagePackage nmp = new NotifyMessagePackage();
            nmp.agent = agent;
            nmp.args = args;
            nmp.processInstanceId = processInstanceId;
            this.pendingMessage.add(nmp);
        } else {
            log.warn("[" + processInstanceId + "]Add item to a sending AgentNotifyPlugin, ignored.");
        }
    }

    /**
     * Clear all pending message.
     */
    public void clear(String processInstanceId) {
        if (!this.isBeginSending) {
            this.pendingMessage.clear();
        } else {
            log.warn("[" + processInstanceId + "]Try to clear items in a sending AgentNotifyPlugin, ignored.");
        }
    }

    /**
     * Count pending message.
     *
     * @return number of pending queue, -1 if begin to send.
     */
    public int count(String processInstanceId) {
        if (!this.isBeginSending) {
            return this.pendingMessage.size();
        } else {
            log.warn("[" + processInstanceId + "]Try to count items in a sending AgentNotifyPlugin, ignored.");
            return -1;
        }
    }

    /**
     * Handle pending message queue and post them.
     */
    private void handlePendingMessage() {
        this.isBeginSending = true;
        for (NotifyMessagePackage nmp : this.pendingMessage) {
            this.postToAgent(nmp.agent, nmp.args, nmp.processInstanceId);
        }
    }

    /**
     * Post data to a agent binding URL.
     *
     * @param agent             agent participant context
     * @param args              arguments to post
     * @param processInstanceId process instance id
     */
    private void postToAgent(ProcessParticipant agent, Map<String, String> args, String processInstanceId) {
        try {
            // reentrant agent: send post directly.
            if (agent.getReentrantType() == AgentReentrantType.Reentrant.ordinal()) {
                restTemplate.postForObject(agent.getAgentLocation(), args, String.class);
                log.info(String.format("[%s]Send notification to agent %s(%s): %s", processInstanceId, agent.getDisplayName(), agent.getAccountId(), args));
            }
            // not reentrant agent: queue the request.
            else {
                // todo here should use queue to send asynchronously only when the agent is free (include other RS)
                restTemplate.postForObject(agent.getAgentLocation(), args, String.class);
            }
        } catch (Exception ex) {
            // do not rethrown the exception, since this is tolerable.
            log.warn("[" + processInstanceId + "]Send notification to agent failed, " + ex, AgentNotifyPlugin.class.getName());
        }
    }

    /**
     * Data package for notification.
     */
    private class NotifyMessagePackage {
        /**
         * Get or set notification send to agent context.
         */
        ProcessParticipant agent;

        /**
         * Get or set notification arguments map.
         */
        Map<String, String> args;

        /**
         * Get or set notification process instance id.
         */
        String processInstanceId;
    }
}
