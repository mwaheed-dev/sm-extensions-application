package com.simplerishta.cms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplerishta.cms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditTrailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditTrail.class);
        AuditTrail auditTrail1 = new AuditTrail();
        auditTrail1.setId(1L);
        AuditTrail auditTrail2 = new AuditTrail();
        auditTrail2.setId(auditTrail1.getId());
        assertThat(auditTrail1).isEqualTo(auditTrail2);
        auditTrail2.setId(2L);
        assertThat(auditTrail1).isNotEqualTo(auditTrail2);
        auditTrail1.setId(null);
        assertThat(auditTrail1).isNotEqualTo(auditTrail2);
    }
}
