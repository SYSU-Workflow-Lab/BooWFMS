package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.resource.dao.ServiceInfoDAO;
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

}
