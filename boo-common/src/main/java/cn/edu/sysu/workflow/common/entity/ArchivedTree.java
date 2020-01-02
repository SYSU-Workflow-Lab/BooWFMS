package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * TODO ArchivedTree of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class ArchivedTree extends BooPagedQuery {

    private static final long serialVersionUID = -9211141097660527667L;
    public static final String PREFIX = "pi-";

    /**
     * 所属流程实例ID & 数据库主键
     */
    private String processInstanceId;

    /**
     * 树数据
     */
    private String tree;

    public ArchivedTree() {
        super();
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArchivedTree that = (ArchivedTree) o;
        return Objects.equals(processInstanceId, that.processInstanceId) &&
                Objects.equals(tree, that.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processInstanceId, tree);
    }
}
