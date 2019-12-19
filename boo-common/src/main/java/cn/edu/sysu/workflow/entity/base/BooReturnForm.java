package cn.edu.sysu.workflow.entity.base;

import java.io.Serializable;

/**
 * Created by Skye on 2019/12/18.
 */
public class BooReturnForm implements Serializable {

    private static final long serialVersionUID = -6691674972794875954L;

    /**
     * 返回包信息
     */
    private String message;

    /**
     * 返回包内容
     */
    private Object data;

    public BooReturnForm() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
