package cn.edu.sysu.workflow.engine.core.model.extend;

import cn.edu.sysu.workflow.engine.core.ActionExecutionContext;
import cn.edu.sysu.workflow.engine.core.model.Action;

import java.io.Serializable;

/**
 * Author: Rinkako
 * Date  : 2017/6/15
 * Usage : Label context of Resource.
 */
public class Resource extends Action implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The resource name
     */
    private String name;

    /**
     * The type name: human or nonhuman
     */
    private String type;

    /**
     * The count of this resource
     */
    private String count;

    /**
     * The roleId of this resource
     */
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void execute(ActionExecutionContext exctx) {

    }
}
