package com.simplerishta.cms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuditTrail.
 */
@Entity
@Table(name = "audit_trail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditTrail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 12)
    @Column(name = "tracking_id", length = 12, nullable = false)
    private String trackingId;

    @NotNull
    @Size(max = 12)
    @Column(name = "user_id", length = 12, nullable = false)
    private String userId;

    @NotNull
    @Size(max = 12)
    @Column(name = "user_ip", length = 12, nullable = false)
    private String userIp;

    @NotNull
    @Size(max = 3)
    @Column(name = "country_code", length = 3, nullable = false)
    private String countryCode;

    @NotNull
    @Size(max = 12)
    @Column(name = "action_type", length = 12, nullable = false)
    private String actionType;

    @Size(max = 500)
    @Column(name = "action_detail", length = 500)
    private String actionDetail;

    @Column(name = "failed_reason")
    private String failedReason;

    @NotNull
    @Column(name = "action_time_stamp", nullable = false)
    private Instant actionTimeStamp;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Size(max = 30)
    @Column(name = "created_by", length = 30)
    private String createdBy;

    @NotNull
    @Column(name = "created_time_stamp", nullable = false)
    private Instant createdTimeStamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuditTrail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return this.trackingId;
    }

    public AuditTrail trackingId(String trackingId) {
        this.setTrackingId(trackingId);
        return this;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getUserId() {
        return this.userId;
    }

    public AuditTrail userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public AuditTrail userIp(String userIp) {
        this.setUserIp(userIp);
        return this;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public AuditTrail countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getActionType() {
        return this.actionType;
    }

    public AuditTrail actionType(String actionType) {
        this.setActionType(actionType);
        return this;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionDetail() {
        return this.actionDetail;
    }

    public AuditTrail actionDetail(String actionDetail) {
        this.setActionDetail(actionDetail);
        return this;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String getFailedReason() {
        return this.failedReason;
    }

    public AuditTrail failedReason(String failedReason) {
        this.setFailedReason(failedReason);
        return this;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Instant getActionTimeStamp() {
        return this.actionTimeStamp;
    }

    public AuditTrail actionTimeStamp(Instant actionTimeStamp) {
        this.setActionTimeStamp(actionTimeStamp);
        return this;
    }

    public void setActionTimeStamp(Instant actionTimeStamp) {
        this.actionTimeStamp = actionTimeStamp;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public AuditTrail status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AuditTrail createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTimeStamp() {
        return this.createdTimeStamp;
    }

    public AuditTrail createdTimeStamp(Instant createdTimeStamp) {
        this.setCreatedTimeStamp(createdTimeStamp);
        return this;
    }

    public void setCreatedTimeStamp(Instant createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditTrail)) {
            return false;
        }
        return id != null && id.equals(((AuditTrail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditTrail{" +
            "id=" + getId() +
            ", trackingId='" + getTrackingId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", userIp='" + getUserIp() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", actionType='" + getActionType() + "'" +
            ", actionDetail='" + getActionDetail() + "'" +
            ", failedReason='" + getFailedReason() + "'" +
            ", actionTimeStamp='" + getActionTimeStamp() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTimeStamp='" + getCreatedTimeStamp() + "'" +
            "}";
    }
}
