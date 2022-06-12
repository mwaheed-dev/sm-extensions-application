package com.simplerishta.cms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditTrailMapperTest {

    private AuditTrailMapper auditTrailMapper;

    @BeforeEach
    public void setUp() {
        auditTrailMapper = new AuditTrailMapperImpl();
    }
}
