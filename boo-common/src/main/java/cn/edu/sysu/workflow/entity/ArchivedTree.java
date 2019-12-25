package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * ArchivedTree of BooWFMS.
 *
 * Created by Skye on 2019/12/18.
 */
public class ArchivedTree extends BooPagedQuery {

    private static final long serialVersionUID = -9211141097660527667L;
    public static final String PREFIX = "at-";

    /**
     * TODO ID
     */
    private String archivedTreeId;

    /**
     * 所属流程实例ID
     */
    private String processInstanceId;

    /**
     * 树数据
     */
    private String tree;

    public ArchivedTree() {
        this.archivedTreeId = PREFIX + IdUtil.nextId();
    }

    public String getArchivedTreeId() {
        return archivedTreeId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchivedTree that = (ArchivedTree) o;
        return Objects.equals(archivedTreeId, that.archivedTreeId) &&
                Objects.equals(processInstanceId, that.processInstanceId) &&
                Objects.equals(tree, that.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivedTreeId, processInstanceId, tree);
    }
}
