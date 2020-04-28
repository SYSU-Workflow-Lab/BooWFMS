package cn.edu.sysu.workflow.common.entity.exception;

/**
 * 服务层异常
 *
 * @author Skye
 * Created on 2020/1/2
 */
public class ServiceFailureException extends RuntimeException {

    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
