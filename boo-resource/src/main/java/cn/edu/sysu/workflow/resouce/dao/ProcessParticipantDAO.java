package cn.edu.sysu.workflow.resouce.dao;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;

/**
 * ProcessParticipant数据库操作
 *
 * @author Skye
 * Created on 2020/1/18
 */
public interface ProcessParticipantDAO {

    /**
     * 根据账户Id查找ProcessParticipant数据
     * @param accountId
     * @return
     */
    ProcessParticipant findByAccountId(String accountId);

}
