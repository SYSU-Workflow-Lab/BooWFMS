package cn.edu.sysu.workflow.entity.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 计数提取器
 *
 * @author Skye
 * Created on 2019/12/27
 */
public class BooCountExtractor implements ResultSetExtractor<Integer> {

    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

}
