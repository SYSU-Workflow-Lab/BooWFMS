package cn.edu.sysu.workflow.entity.access;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * Role of BooWFMS.
 *
 * Created by Skye on 2019/12/17.
 */
public class Role extends BooPagedQuery {

    private static final long serialVersionUID = -5426221066866544296L;
    public static final String PREFIX = "role-";

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色等级
     */
    private String level;

    /**
     * 角色描述
     */
    private String description;

    public Role() {
        this.roleId = PREFIX + IdUtil.nextId();
    }

    public String getRoleId() {
        return roleId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) &&
                Objects.equals(level, role.level) &&
                Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, level, description);
    }
}
