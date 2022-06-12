package com.simplerishta.cms.service.mapper;

import com.simplerishta.cms.domain.AuditTrail;
import com.simplerishta.cms.service.dto.AuditTrailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditTrail} and its DTO {@link AuditTrailDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditTrailMapper extends EntityMapper<AuditTrailDTO, AuditTrail> {}
