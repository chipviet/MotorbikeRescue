package com.chipviet.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chipviet.project.IntegrationTest;
import com.chipviet.project.domain.RescueTeam;
import com.chipviet.project.repository.RescueTeamRepository;
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
 * Integration tests for the {@link RescueTeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RescueTeamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_AT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rescue-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RescueTeamRepository rescueTeamRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRescueTeamMockMvc;

    private RescueTeam rescueTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RescueTeam createEntity(EntityManager em) {
        RescueTeam rescueTeam = new RescueTeam()
            .name(DEFAULT_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .address(DEFAULT_ADDRESS)
            .createAt(DEFAULT_CREATE_AT);
        return rescueTeam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RescueTeam createUpdatedEntity(EntityManager em) {
        RescueTeam rescueTeam = new RescueTeam()
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .address(UPDATED_ADDRESS)
            .createAt(UPDATED_CREATE_AT);
        return rescueTeam;
    }

    @BeforeEach
    public void initTest() {
        rescueTeam = createEntity(em);
    }

    @Test
    @Transactional
    void createRescueTeam() throws Exception {
        int databaseSizeBeforeCreate = rescueTeamRepository.findAll().size();
        // Create the RescueTeam
        restRescueTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rescueTeam)))
            .andExpect(status().isCreated());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeCreate + 1);
        RescueTeam testRescueTeam = rescueTeamList.get(rescueTeamList.size() - 1);
        assertThat(testRescueTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRescueTeam.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRescueTeam.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testRescueTeam.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testRescueTeam.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testRescueTeam.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void createRescueTeamWithExistingId() throws Exception {
        // Create the RescueTeam with an existing ID
        rescueTeam.setId(1L);

        int databaseSizeBeforeCreate = rescueTeamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRescueTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rescueTeam)))
            .andExpect(status().isBadRequest());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRescueTeams() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        // Get all the rescueTeamList
        restRescueTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rescueTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT)));
    }

    @Test
    @Transactional
    void getRescueTeam() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        // Get the rescueTeam
        restRescueTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, rescueTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rescueTeam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT));
    }

    @Test
    @Transactional
    void getNonExistingRescueTeam() throws Exception {
        // Get the rescueTeam
        restRescueTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRescueTeam() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();

        // Update the rescueTeam
        RescueTeam updatedRescueTeam = rescueTeamRepository.findById(rescueTeam.getId()).get();
        // Disconnect from session so that the updates on updatedRescueTeam are not directly saved in db
        em.detach(updatedRescueTeam);
        updatedRescueTeam
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .address(UPDATED_ADDRESS)
            .createAt(UPDATED_CREATE_AT);

        restRescueTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRescueTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRescueTeam))
            )
            .andExpect(status().isOk());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
        RescueTeam testRescueTeam = rescueTeamList.get(rescueTeamList.size() - 1);
        assertThat(testRescueTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRescueTeam.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRescueTeam.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testRescueTeam.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testRescueTeam.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testRescueTeam.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void putNonExistingRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rescueTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rescueTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rescueTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rescueTeam)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRescueTeamWithPatch() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();

        // Update the rescueTeam using partial update
        RescueTeam partialUpdatedRescueTeam = new RescueTeam();
        partialUpdatedRescueTeam.setId(rescueTeam.getId());

        partialUpdatedRescueTeam.name(UPDATED_NAME).phoneNumber(UPDATED_PHONE_NUMBER).longitude(UPDATED_LONGITUDE);

        restRescueTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRescueTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRescueTeam))
            )
            .andExpect(status().isOk());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
        RescueTeam testRescueTeam = rescueTeamList.get(rescueTeamList.size() - 1);
        assertThat(testRescueTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRescueTeam.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRescueTeam.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testRescueTeam.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testRescueTeam.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testRescueTeam.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void fullUpdateRescueTeamWithPatch() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();

        // Update the rescueTeam using partial update
        RescueTeam partialUpdatedRescueTeam = new RescueTeam();
        partialUpdatedRescueTeam.setId(rescueTeam.getId());

        partialUpdatedRescueTeam
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .address(UPDATED_ADDRESS)
            .createAt(UPDATED_CREATE_AT);

        restRescueTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRescueTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRescueTeam))
            )
            .andExpect(status().isOk());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
        RescueTeam testRescueTeam = rescueTeamList.get(rescueTeamList.size() - 1);
        assertThat(testRescueTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRescueTeam.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRescueTeam.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testRescueTeam.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testRescueTeam.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testRescueTeam.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void patchNonExistingRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rescueTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rescueTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rescueTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRescueTeam() throws Exception {
        int databaseSizeBeforeUpdate = rescueTeamRepository.findAll().size();
        rescueTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRescueTeamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rescueTeam))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RescueTeam in the database
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRescueTeam() throws Exception {
        // Initialize the database
        rescueTeamRepository.saveAndFlush(rescueTeam);

        int databaseSizeBeforeDelete = rescueTeamRepository.findAll().size();

        // Delete the rescueTeam
        restRescueTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, rescueTeam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RescueTeam> rescueTeamList = rescueTeamRepository.findAll();
        assertThat(rescueTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
