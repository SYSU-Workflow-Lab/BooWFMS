package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.access.Authority;

/**
 * 权限数据库操作
 *
 * @author Skye
 * Created on 2020/6/10
 */
public interface AuthorityDAO {

    /**
     * 创建工作项
     *
     * @param authority <ul>
     *                  <li>authorityId</li>
     *                  <li>type</li>
     *                  <li>accountId</li>
     *                  <li>businessProcessEntityId</li>
     *                  </ul>
     * @return 新增的数据量
     */
    int save(Authority authority);


}
