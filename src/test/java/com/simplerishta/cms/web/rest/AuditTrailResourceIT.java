package com.simplerishta.cms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplerishta.cms.IntegrationTest;
import com.simplerishta.cms.domain.AuditTrail;
import com.simplerishta.cms.repository.AuditTrailRepository;
import com.simplerishta.cms.service.dto.AuditTrailDTO;
import com.simplerishta.cms.service.mapper.AuditTrailMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuditTrailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditTrailResourceIT {

    private static final String DEFAULT_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_IP = "AAAAAAAAAA";
    private static final String UPDATED_USER_IP = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAA";
    private static final String UPDATED_COUNTRY_CODE = "BBB";

    private static final String DEFAULT_ACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FAILED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_FAILED_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/audit-trails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Autowired
    private AuditTrailMapper auditTrailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditTrailMockMvc;

    private AuditTrail auditTrail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditTrail createEntity(EntityManager em) {
        AuditTrail auditTrail = new AuditTrail()
            .trackingId(DEFAULT_TRACKING_ID)
            .userId(DEFAULT_USER_ID)
            .userIp(DEFAULT_USER_IP)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .actionType(DEFAULT_ACTION_TYPE)
            .actionDetail(DEFAULT_ACTION_DETAIL)
            .failedReason(DEFAULT_FAILED_REASON)
            .actionTimeStamp(DEFAULT_ACTION_TIME_STAMP)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTimeStamp(DEFAULT_CREATED_TIME_STAMP);
        return auditTrail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditTrail createUpdatedEntity(EntityManager em) {
        AuditTrail auditTrail = new AuditTrail()
            .trackingId(UPDATED_TRACKING_ID)
            .userId(UPDATED_USER_ID)
            .userIp(UPDATED_USER_IP)
            .countryCode(UPDATED_COUNTRY_CODE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionDetail(UPDATED_ACTION_DETAIL)
            .failedReason(UPDATED_FAILED_REASON)
            .actionTimeStamp(UPDATED_ACTION_TIME_STAMP)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTimeStamp(UPDATED_CREATED_TIME_STAMP);
        return auditTrail;
    }

    @BeforeEach
    public void initTest() {
        auditTrail = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditTrail() throws Exception {
        int databaseSizeBeforeCreate = auditTrailRepository.findAll().size();
        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);
        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeCreate + 1);
        AuditTrail testAuditTrail = auditTrailList.get(auditTrailList.size() - 1);
        assertThat(testAuditTrail.getTrackingId()).isEqualTo(DEFAULT_TRACKING_ID);
        assertThat(testAuditTrail.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAuditTrail.getUserIp()).isEqualTo(DEFAULT_USER_IP);
        assertThat(testAuditTrail.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testAuditTrail.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testAuditTrail.getActionDetail()).isEqualTo(DEFAULT_ACTION_DETAIL);
        assertThat(testAuditTrail.getFailedReason()).isEqualTo(DEFAULT_FAILED_REASON);
        assertThat(testAuditTrail.getActionTimeStamp()).isEqualTo(DEFAULT_ACTION_TIME_STAMP);
        assertThat(testAuditTrail.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuditTrail.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAuditTrail.getCreatedTimeStamp()).isEqualTo(DEFAULT_CREATED_TIME_STAMP);
    }

    @Test
    @Transactional
    void createAuditTrailWithExistingId() throws Exception {
        // Create the AuditTrail with an existing ID
        auditTrail.setId(1L);
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        int databaseSizeBeforeCreate = auditTrailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setTrackingId(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setUserId(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setUserIp(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setCountryCode(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setActionType(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActionTimeStampIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setActionTimeStamp(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setStatus(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeStampIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTrailRepository.findAll().size();
        // set the field null
        auditTrail.setCreatedTimeStamp(null);

        // Create the AuditTrail, which fails.
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        restAuditTrailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isBadRequest());

        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditTrails() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        // Get all the auditTrailList
        restAuditTrailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditTrail.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingId").value(hasItem(DEFAULT_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userIp").value(hasItem(DEFAULT_USER_IP)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].actionDetail").value(hasItem(DEFAULT_ACTION_DETAIL)))
            .andExpect(jsonPath("$.[*].failedReason").value(hasItem(DEFAULT_FAILED_REASON)))
            .andExpect(jsonPath("$.[*].actionTimeStamp").value(hasItem(DEFAULT_ACTION_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTimeStamp").value(hasItem(DEFAULT_CREATED_TIME_STAMP.toString())));
    }

    @Test
    @Transactional
    void getAuditTrail() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        // Get the auditTrail
        restAuditTrailMockMvc
            .perform(get(ENTITY_API_URL_ID, auditTrail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditTrail.getId().intValue()))
            .andExpect(jsonPath("$.trackingId").value(DEFAULT_TRACKING_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userIp").value(DEFAULT_USER_IP))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE))
            .andExpect(jsonPath("$.actionDetail").value(DEFAULT_ACTION_DETAIL))
            .andExpect(jsonPath("$.failedReason").value(DEFAULT_FAILED_REASON))
            .andExpect(jsonPath("$.actionTimeStamp").value(DEFAULT_ACTION_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTimeStamp").value(DEFAULT_CREATED_TIME_STAMP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAuditTrail() throws Exception {
        // Get the auditTrail
        restAuditTrailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuditTrail() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();

        // Update the auditTrail
        AuditTrail updatedAuditTrail = auditTrailRepository.findById(auditTrail.getId()).get();
        // Disconnect from session so that the updates on updatedAuditTrail are not directly saved in db
        em.detach(updatedAuditTrail);
        updatedAuditTrail
            .trackingId(UPDATED_TRACKING_ID)
            .userId(UPDATED_USER_ID)
            .userIp(UPDATED_USER_IP)
            .countryCode(UPDATED_COUNTRY_CODE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionDetail(UPDATED_ACTION_DETAIL)
            .failedReason(UPDATED_FAILED_REASON)
            .actionTimeStamp(UPDATED_ACTION_TIME_STAMP)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTimeStamp(UPDATED_CREATED_TIME_STAMP);
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(updatedAuditTrail);

        restAuditTrailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditTrailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
        AuditTrail testAuditTrail = auditTrailList.get(auditTrailList.size() - 1);
        assertThat(testAuditTrail.getTrackingId()).isEqualTo(UPDATED_TRACKING_ID);
        assertThat(testAuditTrail.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAuditTrail.getUserIp()).isEqualTo(UPDATED_USER_IP);
        assertThat(testAuditTrail.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAuditTrail.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testAuditTrail.getActionDetail()).isEqualTo(UPDATED_ACTION_DETAIL);
        assertThat(testAuditTrail.getFailedReason()).isEqualTo(UPDATED_FAILED_REASON);
        assertThat(testAuditTrail.getActionTimeStamp()).isEqualTo(UPDATED_ACTION_TIME_STAMP);
        assertThat(testAuditTrail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditTrail.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuditTrail.getCreatedTimeStamp()).isEqualTo(UPDATED_CREATED_TIME_STAMP);
    }

    @Test
    @Transactional
    void putNonExistingAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditTrailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditTrailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditTrailWithPatch() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();

        // Update the auditTrail using partial update
        AuditTrail partialUpdatedAuditTrail = new AuditTrail();
        partialUpdatedAuditTrail.setId(auditTrail.getId());

        partialUpdatedAuditTrail
            .countryCode(UPDATED_COUNTRY_CODE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionDetail(UPDATED_ACTION_DETAIL)
            .actionTimeStamp(UPDATED_ACTION_TIME_STAMP)
            .createdBy(UPDATED_CREATED_BY)
            .createdTimeStamp(UPDATED_CREATED_TIME_STAMP);

        restAuditTrailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditTrail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditTrail))
            )
            .andExpect(status().isOk());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
        AuditTrail testAuditTrail = auditTrailList.get(auditTrailList.size() - 1);
        assertThat(testAuditTrail.getTrackingId()).isEqualTo(DEFAULT_TRACKING_ID);
        assertThat(testAuditTrail.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAuditTrail.getUserIp()).isEqualTo(DEFAULT_USER_IP);
        assertThat(testAuditTrail.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAuditTrail.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testAuditTrail.getActionDetail()).isEqualTo(UPDATED_ACTION_DETAIL);
        assertThat(testAuditTrail.getFailedReason()).isEqualTo(DEFAULT_FAILED_REASON);
        assertThat(testAuditTrail.getActionTimeStamp()).isEqualTo(UPDATED_ACTION_TIME_STAMP);
        assertThat(testAuditTrail.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuditTrail.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuditTrail.getCreatedTimeStamp()).isEqualTo(UPDATED_CREATED_TIME_STAMP);
    }

    @Test
    @Transactional
    void fullUpdateAuditTrailWithPatch() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();

        // Update the auditTrail using partial update
        AuditTrail partialUpdatedAuditTrail = new AuditTrail();
        partialUpdatedAuditTrail.setId(auditTrail.getId());

        partialUpdatedAuditTrail
            .trackingId(UPDATED_TRACKING_ID)
            .userId(UPDATED_USER_ID)
            .userIp(UPDATED_USER_IP)
            .countryCode(UPDATED_COUNTRY_CODE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionDetail(UPDATED_ACTION_DETAIL)
            .failedReason(UPDATED_FAILED_REASON)
            .actionTimeStamp(UPDATED_ACTION_TIME_STAMP)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTimeStamp(UPDATED_CREATED_TIME_STAMP);

        restAuditTrailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditTrail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditTrail))
            )
            .andExpect(status().isOk());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
        AuditTrail testAuditTrail = auditTrailList.get(auditTrailList.size() - 1);
        assertThat(testAuditTrail.getTrackingId()).isEqualTo(UPDATED_TRACKING_ID);
        assertThat(testAuditTrail.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAuditTrail.getUserIp()).isEqualTo(UPDATED_USER_IP);
        assertThat(testAuditTrail.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAuditTrail.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testAuditTrail.getActionDetail()).isEqualTo(UPDATED_ACTION_DETAIL);
        assertThat(testAuditTrail.getFailedReason()).isEqualTo(UPDATED_FAILED_REASON);
        assertThat(testAuditTrail.getActionTimeStamp()).isEqualTo(UPDATED_ACTION_TIME_STAMP);
        assertThat(testAuditTrail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditTrail.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuditTrail.getCreatedTimeStamp()).isEqualTo(UPDATED_CREATED_TIME_STAMP);
    }

    @Test
    @Transactional
    void patchNonExistingAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditTrailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditTrail() throws Exception {
        int databaseSizeBeforeUpdate = auditTrailRepository.findAll().size();
        auditTrail.setId(count.incrementAndGet());

        // Create the AuditTrail
        AuditTrailDTO auditTrailDTO = auditTrailMapper.toDto(auditTrail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditTrailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditTrailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditTrail in the database
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditTrail() throws Exception {
        // Initialize the database
        auditTrailRepository.saveAndFlush(auditTrail);

        int databaseSizeBeforeDelete = auditTrailRepository.findAll().size();

        // Delete the auditTrail
        restAuditTrailMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditTrail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditTrail> auditTrailList = auditTrailRepository.findAll();
        assertThat(auditTrailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
