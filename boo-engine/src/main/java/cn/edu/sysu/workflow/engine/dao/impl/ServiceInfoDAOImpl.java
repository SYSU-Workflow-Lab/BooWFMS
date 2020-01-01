package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.engine.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.common.entity.ServiceInfo;
import cn.edu.sysu.workflow.common.entity.jdbc.BooPreparedStatementSetter;
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

/**
 * @see cn.edu.sysu.workflow.engine.dao.ServiceInfoDAO
 * @author Skye
 * Created on 2019/12/26
 */
@Repository
public class ServiceInfoDAOImpl implements ServiceInfoDAO {

    private static final Logger log = LoggerFactory.getLogger(ServiceInfoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(ServiceInfo serviceInfo) {
        String sql = "INSERT INTO boo_service_info " +
                "(service_info_id, url, is_active, business, cpu_occupancy_rate, memory_occupancy_rate, " +
                "tomcat_concurrency, work_item_count, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // serviceInfoId
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getServiceInfoId(), Types.VARCHAR);
                    // url
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getUrl(), Types.VARCHAR);
                    // isActive
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.isActive(), Types.BOOLEAN);
                    // business
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getBusiness(), Types.DOUBLE);
                    // cpuOccupancyRate
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getCpuOccupancyRate(), Types.DOUBLE);
                    // memoryOccupancyRate
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getMemoryOccupancyRate(), Types.DOUBLE);
                    // tomcatConcurrency
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getTomcatConcurrency(), Types.DOUBLE);
                    // workItemCount
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getWorkItemCount(), Types.DOUBLE);
                }
            });
        } catch (Exception e) {
            log.error("[" + serviceInfo.getServiceInfoId() + "]Error on creating service info by serviceInfoId.", e);
            return 0;
        }
    }

    @Override
    public int update(ServiceInfo serviceInfo) {
        String sql = "UPDATE boo_service_info SET last_update_timestamp = NOW()";
        // url
        if (!StringUtils.isEmpty(serviceInfo.getUrl())) {
            sql += ", url = ?";
        }
        // isActive
        if (!StringUtils.isEmpty(serviceInfo.isActive())) {
            sql += ", is_active = ?";
        }
        // business
        if (null != serviceInfo.getBusiness()) {
            sql += ", business = ?";
        }
        // cpuOccupancyRate
        if (null != serviceInfo.getCpuOccupancyRate()) {
            sql += ", cpu_occupancy_rate = ?";
        }
        // memoryOccupancyRate
        if (null != serviceInfo.getMemoryOccupancyRate()) {
            sql += ", memory_occupancy_rate = ?";
        }
        // tomcatConcurrency
        if (null != serviceInfo.getTomcatConcurrency()) {
            sql += ", tomcat_concurrency = ?";
        }
        // workItemCount
        if (null != serviceInfo.getWorkItemCount()) {
            sql += ", work_item_count = ?";
        }
        sql += " WHERE service_info_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // url
                    if (!StringUtils.isEmpty(serviceInfo.getUrl())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getUrl(), Types.VARCHAR);
                    }
                    // isActive
                    if (!StringUtils.isEmpty(serviceInfo.isActive())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.isActive(), Types.BOOLEAN);
                    }
                    // business
                    if (null != serviceInfo.getBusiness()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getBusiness(), Types.DOUBLE);
                    }
                    // cpuOccupancyRate
                    if (null != serviceInfo.getCpuOccupancyRate()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getCpuOccupancyRate(), Types.DOUBLE);
                    }
                    // memoryOccupancyRate
                    if (null != serviceInfo.getMemoryOccupancyRate()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getMemoryOccupancyRate(), Types.DOUBLE);
                    }
                    // tomcatConcurrency
                    if (null != serviceInfo.getTomcatConcurrency()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getTomcatConcurrency(), Types.DOUBLE);
                    }
                    // workItemCount
                    if (null != serviceInfo.getWorkItemCount()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getWorkItemCount(), Types.DOUBLE);
                    }
                    // serviceInfoId
                    JdbcUtil.preparedStatementSetter(ps, index(), serviceInfo.getServiceInfoId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + serviceInfo.getServiceInfoId() + "]Error on updating service info by serviceInfoId.", e);
            return 0;
        }
    }

    @Override
    public ServiceInfo findOne(String serviceInfoId) {
        String sql = "SELECT si.service_info_id, si.url, si.is_active, si.business, si.cpu_occupancy_rate," +
                "si.memory_occupancy_rate, si.tomcat_concurrency, si.work_item_count, si.last_update_timestamp " +
                "FROM boo_service_info si " +
                "WHERE si.service_info_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{serviceInfoId}, new RowMapper<ServiceInfo>() {
                @Override
                public ServiceInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setServiceInfoId(resultSet.getString("service_info_id"));
                    serviceInfo.setUrl(resultSet.getString("url"));
                    serviceInfo.setActive(resultSet.getBoolean("is_active"));
                    serviceInfo.setBusiness(resultSet.getDouble("business"));
                    serviceInfo.setCpuOccupancyRate(resultSet.getDouble("cpu_occupancy_rate"));
                    serviceInfo.setMemoryOccupancyRate(resultSet.getDouble("memory_occupancy_rate"));
                    serviceInfo.setTomcatConcurrency(resultSet.getDouble("tomcat_concurrency"));
                    serviceInfo.setWorkItemCount(resultSet.getDouble("work_item_count"));
                    serviceInfo.setLastUpdateTimestamp(resultSet.getTimestamp("last_update_timestamp"));
                    return serviceInfo;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + serviceInfoId + "]Error on querying service info by serviceInfoId.", e);
            return null;
        }
    }

    @Override
    public int deleteByServiceInfoId(String serviceInfoId) {
        String sql = "DELETE FROM boo_service_info WHERE service_info_id = ?";
        try {
            return jdbcTemplate.update(sql, serviceInfoId);
        } catch (Exception e) {
            log.error("[" + serviceInfoId + "]Error on deleting service info by serviceInfoId.", e);
            return 0;
        }
    }

    @Override
    public String findResourceServiceUrlByProcessInstanceId(String processInstanceId) {
        String sql = "SELECT si.url FROM boo_service_info si, boo_process_instance pi " +
                "WHERE pi.process_instance_id = ? AND pi.resource_service_id = si.service_info_id";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{processInstanceId}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("url");
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying resource service url by processInstanceId.", e);
            return null;
        }
    }

}
