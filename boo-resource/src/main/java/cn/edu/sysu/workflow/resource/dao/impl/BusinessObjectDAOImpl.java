package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.resource.dao.BusinessObjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link BusinessObjectDAO}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@Repository
public class BusinessObjectDAOImpl implements BusinessObjectDAO {

    private static final Logger log = LoggerFactory.getLogger(BusinessObjectDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BusinessObject findOne(String businessObjectId) {
        String sql = "SELECT business_object_id, business_object_name, process_id, status, content, serialization, business_roles " +
                "FROM boo_business_object WHERE business_object_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{businessObjectId}, new RowMapper<BusinessObject>() {
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
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + businessObjectId + "]Error on querying business object by businessObjectId.", e);
            throw new DAOException(e);
        }
    }

}
