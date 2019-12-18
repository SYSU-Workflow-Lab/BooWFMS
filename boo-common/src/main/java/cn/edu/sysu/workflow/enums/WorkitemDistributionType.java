/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package cn.edu.sysu.workflow.enums;

import java.io.Serializable;

/**
 * Author: Rinkako
 * Date  : 2018/2/6
 * Usage : Enum of workitem distribution type
 */
public enum WorkitemDistributionType implements Serializable {
    Allocate,
    Offer,
    AutoAllocateIfOfferFailed
}
