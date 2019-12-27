package cn.edu.sysu.workflow.dao.impl;

import cn.edu.sysu.workflow.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.entity.ServiceInfo;
import cn.edu.sysu.workflow.entity.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Skye
 * Created on 2019/12/26
 */
@Repository
public class ServiceInfoDAOImpl implements ServiceInfoDAO {

    private static final Logger log = LoggerFactory.getLogger(ServiceInfoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ServiceInfo saveOrUpdate(ServiceInfo serviceInfo) {
        return null;
    }

    @Override
    public ServiceInfo findByServiceInfoId(String serviceInfoId) {
        String sql = "SELECT si.service_info_id, si.url, si.is_active, si.business, si.cpu_occupancy_rate," +
                "si.memory_occupancy_rate, si.tomcat_concurrency, si.work_item_count" +
                "FROM boo_service_info si" +
                "WHERE si.service_info_id = ?";
        try {
            return jdbcTemplate.query(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // serviceInfoId
                    if (!StringUtils.isEmpty(serviceInfoId)) {
                        JdbcUtil.preparedStatementSetter(ps, index(), serviceInfoId, Types.VARCHAR);
                    }
                }
            }, new ResultSetExtractor<ServiceInfo>() {
                @Override
                public ServiceInfo extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setServiceInfoId(resultSet.getString("service_info_id"));
                    serviceInfo.setUrl(resultSet.getString("url"));
                    serviceInfo.setActive(resultSet.getBoolean("is_active"));
                    serviceInfo.setBusiness(resultSet.getDouble("business"));
                    serviceInfo.setCpuOccupancyRate(resultSet.getDouble("cpu_occupancy_rate"));
                    serviceInfo.setMemoryOccupancyRate(resultSet.getDouble("memory_occupancy_rate"));
                    serviceInfo.setTomcatConcurrency(resultSet.getDouble("tomcat_concurrency"));
                    serviceInfo.setWorkItemCount(resultSet.getDouble("work_item_count"));
                    return serviceInfo;
                }
            });
        } catch (Exception e) {
            log.error("[boo-engine] Error on querying service info by serviceInfoId.", e);
            return null;
        }
    }

    @Override
    public ServiceInfo deleteByServiceInfoId(String serviceInfoId) {
        return null;
    }

}
