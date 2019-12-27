package cn.edu.sysu.workflow.entity.base;

import java.io.Serializable;

/**
 * Paging Query Base Class for BooWFMS
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class BooPagedQuery implements Serializable {

    private static final long serialVersionUID = 5477672572425853275L;

    /**
     * 分页查询个数
     */
    private Integer limit;

    /**
     * 分页查询偏移量
     */
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
