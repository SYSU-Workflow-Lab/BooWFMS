package cn.edu.sysu.workflow.engine.core.instanceTree;

import cn.edu.sysu.workflow.engine.core.BOXMLExecutionContext;
import cn.edu.sysu.workflow.engine.core.BOXMLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Maintaining all running BO trees and providing methods for service operations.
 *
 * @author Skye
 * Created on 2020/1/1
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
    public static BOXMLExecutor getExecutor(String processInstanceId, String nodeId) {
        return InstanceManager.getExecContext(processInstanceId, nodeId).getSCXMLExecutor();
    }

    /**
     * Get the execution context of a tree node by tree node global id.
     *
     * @param processInstanceId   process instance id
     * @param nodeId tree node global id
     * @return execution context at fetched node
     */
    public static BOXMLExecutionContext getExecContext(String processInstanceId, String nodeId) {
        return InstanceManager.getInstanceTree(processInstanceId).GetNodeById(nodeId).getExect();
    }

    /**
     * Get tree by its global id, means processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return tree reference
     */
    public static RInstanceTree getInstanceTree(String processInstanceId) {
        return InstanceManager.getInstanceTree(processInstanceId, true);
    }

    /**
     * Get tree by its global id, means processInstanceId.
     *
     * @param processInstanceId    process instance id
     * @param warning produce warning message when missing tree
     * @return tree reference
     */
    public static RInstanceTree getInstanceTree(String processInstanceId, boolean warning) {
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
    public static boolean containsInstanceTree(String processInstanceId) {
        return InstanceManager.InstanceTreeTable.containsKey(processInstanceId);
    }


    /**
     * Register a new tree.
     *
     * @param processInstanceId process instance id
     * @param tree Tree reference
     */
    public static void registerInstanceTree(String processInstanceId, RInstanceTree tree) {
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
    public static void unregisterInstanceTree(String processInstanceId) {
        InstanceManager.InstanceTreeTable.remove(processInstanceId);
    }

    /**
     * Forest of instance tree, mapping (ProcessInstanceId, Tree).
     */
    private static ConcurrentHashMap<String, RInstanceTree> InstanceTreeTable = new ConcurrentHashMap<>();
}