package cn.edu.sysu.workflow.common.entity.base;

import java.io.Serializable;
import java.sql.Timestamp;

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

    /**
     * 创建时间戳
     */
    private Timestamp createTimestamp;

    /**
     * 最后修改时间戳
     */
    private Timestamp lastUpdateTimestamp;

    public BooPagedQuery() {
        this.limit = null;
        this.offset = null;
        this.createTimestamp = null;
        this.lastUpdateTimestamp = null;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }
}
