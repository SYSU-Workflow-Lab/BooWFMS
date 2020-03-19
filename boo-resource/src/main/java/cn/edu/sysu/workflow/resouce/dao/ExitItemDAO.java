package cn.edu.sysu.workflow.resouce.dao;

import cn.edu.sysu.workflow.common.entity.ExitItem;

/**
 * 异常退出工作项数据库操作
 *
 * @author Skye
 * Created on 2020/3/19
 */
public interface ExitItemDAO {

    /**
     * 创建异常退出工作项
     *
     * @param exitItem <ul>
     *                    <li>exitItemId</li>
     *                    <li>workItemId</li>
     *                    <li>processInstanceId</li>
     *                    <li>status</li>
     *                    <li>visibility</li>
     *                    <li>reason</li>
     *                    </ul>
     * @return 新增的数据量
     */
    int save(ExitItem exitItem);

}
