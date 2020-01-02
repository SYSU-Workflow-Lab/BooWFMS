package cn.edu.sysu.workflow.common.entity.exception;

/**
 * DAO层异常
 *
 * @author Skye
 * Created on 2020/1/2
 */
public class DAOException extends RuntimeException {

    public DAOException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
