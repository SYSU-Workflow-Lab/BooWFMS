package cn.edu.sysu.workflow.common.jdbc;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 参数绑定抽象类
 *
 * @author Skye
 * Created on 2019/12/27
 */
public abstract class BooPreparedStatementSetter implements PreparedStatementSetter {

    private int index = 1;

    /**
     * 获取自动增加的字段游标，请按顺序写入字段即可
     *
     * @return 字段游标
     */
    public int index() {
        return index++;
    }

    /**
     * 标准实现setValues
     *
     * @param ps PreparedStatement
     * @throws SQLException SQL异常
     */
    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        customSetValues(ps);
    }

    /**
     * 用户的setValues
     *
     * @param ps 从setValues()透传过来的PreparedStatement
     * @throws SQLException SQL异常
     */
    public abstract void customSetValues(PreparedStatement ps) throws SQLException;

}
