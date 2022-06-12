package com.simplerishta.cms.service;

import com.simplerishta.cms.domain.AuditTrail;
import com.simplerishta.cms.repository.AuditTrailRepository;
import com.simplerishta.cms.service.dto.AuditTrailDTO;
import com.simplerishta.cms.service.mapper.AuditTrailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuditTrail}.
 */
@Service
@Transactional
public class AuditTrailService {

    private final Logger log = LoggerFactory.getLogger(AuditTrailService.class);

    private final AuditTrailRepository auditTrailRepository;

    private final AuditTrailMapper auditTrailMapper;

    public AuditTrailService(AuditTrailRepository auditTrailRepository, AuditTrailMapper auditTrailMapper) {
        this.auditTrailRepository = auditTrailRepository;
        this.auditTrailMapper = auditTrailMapper;
    }

    /**
     * Save a auditTrail.
     *
     * @param auditTrailDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditTrailDTO save(AuditTrailDTO auditTrailDTO) {
        log.debug("Request to save AuditTrail : {}", auditTrailDTO);
        AuditTrail auditTrail = auditTrailMapper.toEntity(auditTrailDTO);
        auditTrail = auditTrailRepository.save(auditTrail);
        return auditTrailMapper.toDto(auditTrail);
    }

    /**
     * Update a auditTrail.
     *
     * @param auditTrailDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditTrailDTO update(AuditTrailDTO auditTrailDTO) {
        log.debug("Request to save AuditTrail : {}", auditTrailDTO);
        AuditTrail auditTrail = auditTrailMapper.toEntity(auditTrailDTO);
        auditTrail = auditTrailRepository.save(auditTrail);
        return auditTrailMapper.toDto(auditTrail);
    }

    /**
     * Partially update a auditTrail.
     *
     * @param auditTrailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditTrailDTO> partialUpdate(AuditTrailDTO auditTrailDTO) {
        log.debug("Request to partially update AuditTrail : {}", auditTrailDTO);

        return auditTrailRepository
            .findById(auditTrailDTO.getId())
            .map(existingAuditTrail -> {
                auditTrailMapper.partialUpdate(existingAuditTrail, auditTrailDTO);

                return existingAuditTrail;
            })
            .map(auditTrailRepository::save)
            .map(auditTrailMapper::toDto);
    }

    /**
     * Get all the auditTrails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditTrailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditTrails");
        return auditTrailRepository.findAll(pageable).map(auditTrailMapper::toDto);
    }

    /**
     * Get one auditTrail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditTrailDTO> findOne(Long id) {
        log.debug("Request to get AuditTrail : {}", id);
        return auditTrailRepository.findById(id).map(auditTrailMapper::toDto);
    }

    /**
     * Delete the auditTrail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditTrail : {}", id);
        auditTrailRepository.deleteById(id);
    }
}
