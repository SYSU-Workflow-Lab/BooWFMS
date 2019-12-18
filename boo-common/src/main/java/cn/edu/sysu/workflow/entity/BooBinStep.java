package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooBinStep extends BooPagedQuery {

    private static final long serialVersionUID = 2776125695679340149L;

    private String binStepId;
    private String runtimeRecordId;
    private String parentId;
    private String notifiableId;
    private byte[] binlog;
}
