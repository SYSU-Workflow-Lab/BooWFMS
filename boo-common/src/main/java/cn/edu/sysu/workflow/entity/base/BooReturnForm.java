package cn.edu.sysu.workflow.entity.base;

import java.io.Serializable;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooReturnForm implements Serializable {

    private static final long serialVersionUID = -6691674972794875954L;

    private String message;
    private String data;

    public BooReturnForm() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
