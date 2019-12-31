package cn.edu.sysu.workflow.engine.core.instanceTree;

import cn.edu.sysu.workflow.engine.core.BOXMLExecutionContext;
import cn.edu.sysu.workflow.engine.core.BOXMLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Rinkako
 * Date  : 2017/3/15
 * Usage : Maintaining all running BO trees and providing methods for service operations.
 */
public class InstanceManager {

    /**
     * log
     */
    private static final Logger log = LoggerFactory.getLogger(InstanceManager.class);

    /**
     * Get the executor of a tree node by tree node global id.
     *
     * @param processInstanceId   process instance id
     * @param nodeId tree node global id
     * @return executor at fetched node
     */
    public static BOXMLExecutor GetExecutor(String processInstanceId, String nodeId) {
        return InstanceManager.GetExecContext(processInstanceId, nodeId).getSCXMLExecutor();
    }

    /**
     * Get the execution context of a tree node by tree node global id.
     *
     * @param processInstanceId   process instance id
     * @param nodeId tree node global id
     * @return execution context at fetched node
     */
    public static BOXMLExecutionContext GetExecContext(String processInstanceId, String nodeId) {
        return InstanceManager.GetInstanceTree(processInstanceId).GetNodeById(nodeId).getExect();
    }

    /**
     * Get tree by its global id, means processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return tree reference
     */
    public static RInstanceTree GetInstanceTree(String processInstanceId) {
        return InstanceManager.GetInstanceTree(processInstanceId, true);
    }

    /**
     * Get tree by its global id, means processInstanceId.
     *
     * @param processInstanceId    process instance id
     * @param warning produce warning message when missing tree
     * @return tree reference
     */
    public static RInstanceTree GetInstanceTree(String processInstanceId, boolean warning) {
        if (InstanceManager.InstanceTreeTable.containsKey(processInstanceId)) {
            return InstanceManager.InstanceTreeTable.get(processInstanceId);
        } else {
            if (warning) {
                log.warn("[" + processInstanceId + "]Instance tree not found: " + processInstanceId);
            }
            return null;
        }
    }

    /**
     * Check if a instance tree is exist.
     *
     * @param processInstanceId process instance id
     * @return whether tree exist or not
     */
    public static boolean ContainsInstanceTree(String processInstanceId) {
        return InstanceManager.InstanceTreeTable.containsKey(processInstanceId);
    }


    /**
     * Register a new tree.
     *
     * @param processInstanceId process instance id
     * @param tree Tree reference
     */
    public static void RegisterInstanceTree(String processInstanceId, RInstanceTree tree) {
        if (tree == null || tree.Root == null) {
            log.error("[" + processInstanceId + "]Instance tree must not null: " + processInstanceId);
        } else if (InstanceManager.InstanceTreeTable.containsKey(processInstanceId)) {
            log.warn("[" + processInstanceId + "]Duplicated Instance tree: " + processInstanceId);
        } else {
            InstanceManager.InstanceTreeTable.put(processInstanceId, tree);
        }
    }

    /**
     * Signal a process runtime over, and remove its instance tree.
     *
     * @param processInstanceId process instance id
     */
    public static void UnregisterInstanceTree(String processInstanceId) {
        InstanceManager.InstanceTreeTable.remove(processInstanceId);
    }

    /**
     * Forest of instance tree, mapping (ProcessInstanceId, Tree).
     */
    private static ConcurrentHashMap<String, RInstanceTree> InstanceTreeTable = new ConcurrentHashMap<>();
}