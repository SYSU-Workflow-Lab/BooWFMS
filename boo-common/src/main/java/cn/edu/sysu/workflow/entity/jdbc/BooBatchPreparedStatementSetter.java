package cn.edu.sysu.workflow.entity.jdbc;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 批量参数绑定抽象类
 *
 * @author Skye
 * Created on 2019/12/27
 */
public abstract class BooBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
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
     * 重置字段游标
     */
    private void resetIndex() {
        this.index = 1;
    }

    /**
     * 标准实现setValues
     *
     * @param ps PreparedStatement
     * @param i 数据行游标
     * @throws SQLException SQL异常
     */
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        resetIndex();
        customSetValues(ps, i);
    }

    /**
     * 用户的setValues
     *
     * @param ps 从setValues()透传过来的PreparedStatement
     * @param i 从setValues()透传过来的i
     * @throws SQLException SQL异常
     */
    public abstract void customSetValues(PreparedStatement ps, int i) throws SQLException;

}
