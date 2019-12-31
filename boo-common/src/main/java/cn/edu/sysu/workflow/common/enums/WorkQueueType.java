package cn.edu.sysu.workflow.common.enums;

/**
 * Enum of Resource event type.
 *
 * @author Skye
 * Created on 2019/12/25
 */
public enum WorkQueueType {
    UNDEFINED,
    OFFERED,
    ALLOCATED,
    STARTED,
    SUSPENDED,
    UNOFFERED,
    WORKLISTED
}
