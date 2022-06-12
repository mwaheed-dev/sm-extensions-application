package com.simplerishta.cms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplerishta.cms.domain.AuditTrail} entity.
 */
public class AuditTrailDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 12)
    private String trackingId;

    @NotNull
    @Size(max = 12)
    private String userId;

    @NotNull
    @Size(max = 12)
    private String userIp;

    @NotNull
    @Size(max = 3)
    private String countryCode;

    @NotNull
    @Size(max = 12)
    private String actionType;

    @Size(max = 500)
    private String actionDetail;

    private String failedReason;

    @NotNull
    private Instant actionTimeStamp;

    @NotNull
    private Boolean status;

    @Size(max = 30)
    private String createdBy;

    @NotNull
    private Instant createdTimeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Instant getActionTimeStamp() {
        return actionTimeStamp;
    }

    public void setActionTimeStamp(Instant actionTimeStamp) {
        this.actionTimeStamp = actionTimeStamp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Instant createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditTrailDTO)) {
            return false;
        }

        AuditTrailDTO auditTrailDTO = (AuditTrailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditTrailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditTrailDTO{" +
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
