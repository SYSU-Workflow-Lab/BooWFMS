package cn.edu.sysu.workflow.entity.base;

import java.io.Serializable;

/**
 * Paging Query Base Class for BooWFMS
 *
 * Created by Skye on 2019/9/18.
 */
public class BooPagedQuery implements Serializable {

    private static final long serialVersionUID = 5477672572425853275L;

    private Integer limit;

    private Integer offset;

    public BooPagedQuery() {
        this.limit = null;
        this.offset = null;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
