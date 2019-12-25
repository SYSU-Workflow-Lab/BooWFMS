package cn.edu.sysu.workflow.enums;

/**
 * Enum for work item execution status.
 *
 * Created by Skye on 2019/12/25.
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
