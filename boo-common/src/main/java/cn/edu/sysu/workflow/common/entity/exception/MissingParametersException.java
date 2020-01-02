package cn.edu.sysu.workflow.common.entity.exception;

import java.util.List;

/**
 * Controller缺失请求参数异常
 *
 * @author Skye
 * Created on 2020/1/2
 */
public class MissingParametersException extends RuntimeException {

    public MissingParametersException(List<String> missingParams) {
        super(buildMessage(missingParams));
    }

    @Override
    public String toString() {
        return getMessage();
    }

    private static String buildMessage(List<String> missingParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("miss required parameters: ");
        for (String s : missingParams) {
            sb.append(s).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2);
    }

}
