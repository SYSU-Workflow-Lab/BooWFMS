package cn.edu.sysu.workflow.enginelb.dao.impl;

import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.enginelb.dao.ServiceInfoDAO;
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
    public String findEngineServiceUrlByRandom() {
        String sql = "SELECT url FROM boo_service_info WHERE locate('engine-', service_info_id) > 0 ORDER BY RAND() LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getString("url"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("Error on querying engine service url by random.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public String findEngineServiceUrlByRoundRobin(int rrCount) {
        String sql = "SELECT url FROM boo_service_info WHERE locate('engine-', service_info_id) > 0 LIMIT ?, 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{rrCount}, (resultSet, i) -> resultSet.getString("url"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("Error on querying engine service url by round robin.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public String findEngineServiceUrlByProcessInstanceId(String processInstanceId) {
        String sql = "SELECT si.url FROM boo_service_info si, boo_process_instance pi " +
                "WHERE pi.process_instance_id = ? AND pi.engine_id = si.service_info_id";
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
            log.error("[" + processInstanceId + "]Error on querying engine service url by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Integer findEngineServiceQuantity() {
        String sql = "SELECT COUNT(*) FROM boo_service_info WHERE locate('engine-', service_info_id) > 0";
        try {
            return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getInt("COUNT(*)"));
        } catch (Exception e) {
            log.error("Error on querying engine service quantity.", e);
            throw new DAOException(e);
        }
    }

}
