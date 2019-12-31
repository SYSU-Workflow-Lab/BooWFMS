package cn.edu.sysu.workflow.common.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Jdbc相关工具类
 *
 * @author Skye
 * Created on 2019/12/27
 */
public class JdbcUtil {

    /**
     * 带空值处理的PreparedStatementSetter工具
     *
     * @param ps PreparedStatement实例
     * @param parameterIndex 参数索引，从1开始
     * @param obj 参数的对象
     * @param sqlType 参数对应的sql类型，使用java.sql.Types取值
     * @throws SQLException 设置出现异常时抛出
     */
    public static void preparedStatementSetter(PreparedStatement ps, int parameterIndex, Object obj, int sqlType) throws SQLException {
        if (null == obj) {
            ps.setNull(parameterIndex, Types.NULL);
        } else {
            ps.setObject(parameterIndex, obj, sqlType);
        }
    }

}
