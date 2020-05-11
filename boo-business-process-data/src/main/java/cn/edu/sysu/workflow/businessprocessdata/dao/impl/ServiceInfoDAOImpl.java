package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.common.entity.ServiceInfo;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link ServiceInfoDAO}
 *
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
            throw new DAOException(e);
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
            throw new DAOException(e);
        }
    }

    @Override
    public int deleteByServiceInfoId(String serviceInfoId) {
        String sql = "DELETE FROM boo_service_info WHERE service_info_id = ?";
        try {
            return jdbcTemplate.update(sql, serviceInfoId);
        } catch (Exception e) {
            log.error("[" + serviceInfoId + "]Error on deleting service info by serviceInfoId.", e);
            throw new DAOException(e);
        }
    }

}
