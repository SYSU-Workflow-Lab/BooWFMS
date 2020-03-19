package cn.edu.sysu.workflow.engine.core.model.extend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Author: Rinkako
 * Date  : 2018/3/3
 * Usage : Label context of Callback.
 */
public class Callback implements Serializable {

    /**
     * Serial version UID.
     */
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * On which work item status type.
     */
    private String on = "";

    /**
     * Notify hook.
     */
    private String hook = "";

    /**
     * Event name.
     */
    private String event = "";

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getHook() {
        return hook;
    }

    public void setHook(String hook) {
        this.hook = hook;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
