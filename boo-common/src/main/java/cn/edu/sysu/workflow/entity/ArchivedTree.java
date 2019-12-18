package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class ArchivedTree extends BooPagedQuery {

    private static final long serialVersionUID = -9211141097660527667L;

    private String archivedTreeId;
    private String runtimeRecordId;
    private String tree;

    public ArchivedTree() {
    }

    public String getArchivedTreeId() {
        return archivedTreeId;
    }

    public void setArchivedTreeId(String archivedTreeId) {
        this.archivedTreeId = archivedTreeId;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
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
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(tree, that.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivedTreeId, runtimeRecordId, tree);
    }
}
