package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessRoleMapDAO;
import cn.edu.sysu.workflow.common.entity.BusinessRoleMap;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link BusinessRoleMapDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@Repository
public class BusinessRoleMapDAOImpl implements BusinessRoleMapDAO {

    private static final Logger log = LoggerFactory.getLogger(BusinessRoleMapDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(BusinessRoleMap businessRoleMap) {
        String sql = "INSERT INTO boo_business_role_map " +
                "(business_role_map_id, process_instance_id, business_role_name, organization_id, mapped_account_id, " +
                "data_version, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessRoleMapId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getBusinessRoleMapId(), Types.VARCHAR);
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getProcessInstanceId(), Types.VARCHAR);
                    // businessRoleName
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getBusinessRoleName(), Types.BLOB);
                    // organizationId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getOrganizationId(), Types.VARCHAR);
                    // mappedAccountId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getMappedAccountId(), Types.VARCHAR);
                    // dataVersion
                    JdbcUtil.preparedStatementSetter(ps, index(), businessRoleMap.getDataVersion(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessRoleMap.getBusinessRoleMapId() + "]Error on creating business role map by businessRoleMapId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<BusinessRoleMap> findBusinessRoleMapsByProcessInstanceId(String processInstanceId) {
        String sql = "SELECT business_role_map_id, process_instance_id, business_role_name, organization_id, mapped_account_id, data_version " +
                "FROM boo_business_role_map " +
                "WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{processInstanceId}, new RowMapper<BusinessRoleMap>() {
                @Override
                public BusinessRoleMap mapRow(ResultSet resultSet, int i) throws SQLException {
                    BusinessRoleMap businessRoleMap = new BusinessRoleMap();
                    businessRoleMap.setBusinessRoleMapId(resultSet.getString("business_role_map_id"));
                    businessRoleMap.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    businessRoleMap.setBusinessRoleName(resultSet.getString("business_role_name"));
                    businessRoleMap.setOrganizationId(resultSet.getString("organization_id"));
                    businessRoleMap.setMappedAccountId(resultSet.getString("mapped_account_id"));
                    businessRoleMap.setDataVersion(resultSet.getString("data_version"));
                    return businessRoleMap;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying business role map by processInstanceId.", e);
            throw new DAOException(e);
        }
    }
}
