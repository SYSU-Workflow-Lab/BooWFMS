package cn.edu.sysu.workflow.enginefeign.dao.impl;

import cn.edu.sysu.workflow.common.entity.ServiceInfo;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.enginefeign.dao.ServiceInfoDAO;
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
            throw new DAOException(e);
        }
    }

}
