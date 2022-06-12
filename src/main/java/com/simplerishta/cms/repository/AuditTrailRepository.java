package com.simplerishta.cms.repository;

import com.simplerishta.cms.domain.AuditTrail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuditTrail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {}
