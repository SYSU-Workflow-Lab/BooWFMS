package cn.edu.sysu.workflow.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooDomain extends BooPagedQuery {

    private static final long serialVersionUID = -1176672623536511029L;

    private String domainId;
    private String name;
    private int level;
    private int status;
    private Timestamp createtimestamp;
    private String corganGateway;
    private String urlsafeSignature;

    public BooDomain() {
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatetimestamp() {
        return createtimestamp;
    }

    public void setCreatetimestamp(Timestamp createtimestamp) {
        this.createtimestamp = createtimestamp;
    }

    public String getCorganGateway() {
        return corganGateway;
    }

    public void setCorganGateway(String corganGateway) {
        this.corganGateway = corganGateway;
    }

    public String getUrlsafeSignature() {
        return urlsafeSignature;
    }

    public void setUrlsafeSignature(String urlsafeSignature) {
        this.urlsafeSignature = urlsafeSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooDomain booDomain = (BooDomain) o;
        return level == booDomain.level &&
                status == booDomain.status &&
                Objects.equals(domainId, booDomain.domainId) &&
                Objects.equals(name, booDomain.name) &&
                Objects.equals(createtimestamp, booDomain.createtimestamp) &&
                Objects.equals(corganGateway, booDomain.corganGateway) &&
                Objects.equals(urlsafeSignature, booDomain.urlsafeSignature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainId, name, level, status, createtimestamp, corganGateway, urlsafeSignature);
    }
}
