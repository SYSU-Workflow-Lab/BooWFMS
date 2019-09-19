package cn.edu.sysu.workflow.entity;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooArchivedTree extends BooPagedQuery {

    private static final long serialVersionUID = -9211141097660527667L;

    private String archivedTreeId;
    private String rtid;
    private String tree;

    public BooArchivedTree() {
    }

    public String getArchivedTreeId() {
        return archivedTreeId;
    }

    public void setArchivedTreeId(String archivedTreeId) {
        this.archivedTreeId = archivedTreeId;
    }

    public String getRtid() {
        return rtid;
    }

    public void setRtid(String rtid) {
        this.rtid = rtid;
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
        BooArchivedTree that = (BooArchivedTree) o;
        return Objects.equals(archivedTreeId, that.archivedTreeId) &&
                Objects.equals(rtid, that.rtid) &&
                Objects.equals(tree, that.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivedTreeId, rtid, tree);
    }
}
