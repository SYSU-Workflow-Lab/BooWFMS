package cn.edu.sysu.workflow.common.enums;

/**
 * Enum of Resource event type.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/25
 */
public enum RSEventType {
    offer,
    allocate,
    start,
    suspend,
    deallocate,
    delegate,
    reallocate,
    skip,
    cancel,
    complete,
    unoffer,
    resume,
    exception_principle,
    exception_lifecycle
}
