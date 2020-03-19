package cn.edu.sysu.workflow.common.enums;

import java.io.Serializable;

/**
 * Enum of work item scheduling type.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/25
 */
public enum WorkitemDistributionType implements Serializable {
    Allocate,
    Offer,
    AutoAllocateIfOfferFailed
}
