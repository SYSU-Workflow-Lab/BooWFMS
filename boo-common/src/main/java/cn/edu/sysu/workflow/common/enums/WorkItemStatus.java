package cn.edu.sysu.workflow.common.enums;

/**
 * Enum for work item execution status.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/25
 */
public enum WorkItemStatus {
    Enabled,
    Fired,
    Executing,
    Complete,
    ForcedComplete,
    Failed,
    Suspended,
    Discarded
}
