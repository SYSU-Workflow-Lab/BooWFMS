package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @see cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO
 * @author Skye
 * Created on 2019/12/31
 */
@Repository
public class BusinessObjectDAOImpl implements BusinessObjectDAO {

    private static final Logger log = LoggerFactory.getLogger(BusinessObjectDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BusinessObject> findBusinessObjectsByProcessId(String processId) {
        String sql = "SELECT business_object_id, business_object_name, process_id, status, content, serialization, business_roles " +
                "FROM boo_business_object " +
                "WHERE process_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{processId}, new RowMapper<BusinessObject>() {
                @Override
                public BusinessObject mapRow(ResultSet resultSet, int i) throws SQLException {
                    BusinessObject businessObject = new BusinessObject();
                    businessObject.setBusinessObjectId(resultSet.getString("business_object_id"));
                    businessObject.setBusinessObjectName(resultSet.getString("business_object_name"));
                    businessObject.setProcessId(resultSet.getString("process_id"));
                    businessObject.setStatus(resultSet.getInt("status"));
                    businessObject.setContent(resultSet.getString("content"));
                    businessObject.setSerialization(resultSet.getBytes("serialization"));
                    businessObject.setBusinessRoles(resultSet.getString("business_roles"));
                    return businessObject;
                }
            });
        } catch (Exception e) {
            log.error("[" + processId + "]Error on querying business object list by processId.", e);
            return null;
        }
    }

}
