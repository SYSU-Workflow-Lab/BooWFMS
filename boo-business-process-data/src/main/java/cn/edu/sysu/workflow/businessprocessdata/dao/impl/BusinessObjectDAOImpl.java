package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessObjectDAO;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

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
    public int save(BusinessObject businessObject) {
        String sql = "INSERT INTO boo_business_object (business_object_id, business_object_name, process_id, status, " +
                "content, serialization, business_roles, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessObjectId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectId(), Types.VARCHAR);
                    // businessObjectName
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectName(), Types.VARCHAR);
                    // processId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getProcessId(), Types.VARCHAR);
                    // status
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getStatus(), Types.INTEGER);
                    // content
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getContent(), Types.VARCHAR);
                    // serialization
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getSerialization(), Types.BLOB);
                    // businessRoles
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessRoles(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessObject.getBusinessObjectId() + "]Error on creating business object by businessObjectId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int update(BusinessObject businessObject) {
        String sql = "UPDATE boo_business_object SET last_update_timestamp = NOW()";
        // businessObjectName
        if (!StringUtils.isEmpty(businessObject.getBusinessObjectName())) {
            sql += ", business_object_name = ?";
        }
        // processId
        if (!StringUtils.isEmpty(businessObject.getProcessId())) {
            sql += ", process_id = ?";
        }
        // status
        if (null != businessObject.getStatus()) {
            sql += ", status = ?";
        }
        // content
        if (!StringUtils.isEmpty(businessObject.getContent())) {
            sql += ", content = ?";
        }
        // serialization
        if (null != businessObject.getSerialization() && 0 != businessObject.getSerialization().length) {
            sql += ", serialization = ?";
        }
        // businessRoles
        if (!StringUtils.isEmpty(businessObject.getBusinessRoles())) {
            sql += ", business_roles = ?";
        }
        // businessObjectId
        sql += " WHERE business_object_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessObjectName
                    if (!StringUtils.isEmpty(businessObject.getBusinessObjectName())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectName(), Types.VARCHAR);
                    }
                    // processId
                    if (!StringUtils.isEmpty(businessObject.getProcessId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getProcessId(), Types.VARCHAR);
                    }
                    // status
                    if (null != businessObject.getStatus()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getStatus(), Types.INTEGER);
                    }
                    // content
                    if (!StringUtils.isEmpty(businessObject.getContent())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getContent(), Types.VARCHAR);
                    }
                    // serialization
                    if (null != businessObject.getSerialization() && 0 != businessObject.getSerialization().length) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getSerialization(), Types.BLOB);
                    }
                    // businessRoles
                    if (!StringUtils.isEmpty(businessObject.getBusinessRoles())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessRoles(), Types.VARCHAR);
                    }
                    // businessObjectId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessObject.getBusinessObjectId() + "]Error on updating business object by businessObjectId.", e);
            throw new DAOException(e);
        }
    }

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

    @Override
    public List<BusinessObject> findBusinessObjectsByProcessId(String businessProcessId) {
        String sql = "SELECT business_object_id, business_object_name, process_id, status, content, serialization, business_roles " +
                "FROM boo_business_object " +
                "WHERE process_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{businessProcessId}, new RowMapper<BusinessObject>() {
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
            log.error("[" + businessProcessId + "]Error on querying business object list by processId.", e);
            throw new DAOException(e);
        }
    }

}
