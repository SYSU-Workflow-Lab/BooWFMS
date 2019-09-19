package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooRolemapArchived extends BooPagedQuery {

    private static final long serialVersionUID = -2803350486212981736L;

    private String rolemapArchivedId;
    private String mapId;
    private String runtimeRecordId;
    private String broleName;
    private String corganGid;
    private String mappedGid;
    private String dataVersion;

    public BooRolemapArchived() {
    }

    public String getRolemapArchivedId() {
        return rolemapArchivedId;
    }

    public void setRolemapArchivedId(String rolemapArchivedId) {
        this.rolemapArchivedId = rolemapArchivedId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public String getBroleName() {
        return broleName;
    }

    public void setBroleName(String broleName) {
        this.broleName = broleName;
    }

    public String getCorganGid() {
        return corganGid;
    }

    public void setCorganGid(String corganGid) {
        this.corganGid = corganGid;
    }

    public String getMappedGid() {
        return mappedGid;
    }

    public void setMappedGid(String mappedGid) {
        this.mappedGid = mappedGid;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooRolemapArchived that = (BooRolemapArchived) o;
        return Objects.equals(rolemapArchivedId, that.rolemapArchivedId) &&
                Objects.equals(mapId, that.mapId) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(broleName, that.broleName) &&
                Objects.equals(corganGid, that.corganGid) &&
                Objects.equals(mappedGid, that.mappedGid) &&
                Objects.equals(dataVersion, that.dataVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolemapArchivedId, mapId, runtimeRecordId, broleName, corganGid, mappedGid, dataVersion);
    }
}
