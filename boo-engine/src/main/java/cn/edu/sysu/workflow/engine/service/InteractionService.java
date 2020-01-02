package cn.edu.sysu.workflow.engine.service;

/**
 * Methods for handling state-machine interaction request outside BO Engine.
 *
 * @author Rinkako, Skye
 * Created on 2020/1/2
 */
public interface InteractionService {

    /**
     * Handle received callback event and dispatch it to destination BO tree node.
     *
     * @param processInstanceId    process instance Id (required)
     * @param bo      from which BO (required)
     * @param on      which callback scene (required)
     * @param event   event send to engine (required)
     * @param payload event send to engine
     */
    void dispatchCallbackByProcessInstanceId(String processInstanceId, String bo, String on, String event, String payload);

    /**
     * Handle received callback event and dispatch it to destination BO tree node.
     *
     * @param processInstanceId    process instance Id (required)
     * @param notifiableId      to which notifiable id (required)
     * @param on      which callback scene (required)
     * @param event   event send to engine (required)
     * @param payload event send to engine
     */
    void dispatchCallbackByNotifiableId(String processInstanceId, String notifiableId, String on, String event, String payload);

}
