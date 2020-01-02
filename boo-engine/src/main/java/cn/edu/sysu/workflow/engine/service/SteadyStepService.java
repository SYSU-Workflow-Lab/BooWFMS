package cn.edu.sysu.workflow.engine.service;

import cn.edu.sysu.workflow.engine.core.BOXMLExecutionContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Methods for making business object service.
 *
 * @author Skye
 * Created on 2019/12/28
 */
public interface SteadyStepService {

    /**
     * Write a entity step to entity memory.
     *
     * @param exctx BOXML execution context
     */
    void writeSteady(BOXMLExecutionContext exctx);

    /**
     * Clear entity step snapshot after final state, and write a span tree descriptor to archived tree table.
     *
     * @param processInstanceId process runtime record id
     */
    void clearSteadyWriteArchivedTree(String processInstanceId);

    /**
     * Resume instances from entity memory, and register it to instance manager.
     *
     * @param processInstanceIdItems processInstanceIds in JSON list
     */
    List<String> resumeSteadyMany(String processInstanceIdItems);

    /**
     * Resume a instance from entity memory, and register it to instance manager.
     *
     * @param processInstanceId
     */
    boolean resumeSteady(String processInstanceId);
}
