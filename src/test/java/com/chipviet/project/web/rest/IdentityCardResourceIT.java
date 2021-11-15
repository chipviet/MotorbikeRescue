package com.chipviet.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chipviet.project.IntegrationTest;
import com.chipviet.project.domain.IdentityCard;
import com.chipviet.project.repository.IdentityCardRepository;
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
 * Integration tests for the {@link IdentityCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdentityCardResourceIT {

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOB = "AAAAAAAAAA";
    private static final String UPDATED_DOB = "BBBBBBBBBB";

    private static final String DEFAULT_HOME = "AAAAAAAAAA";
    private static final String UPDATED_HOME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_DOE = "AAAAAAAAAA";
    private static final String UPDATED_DOE = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/identity-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdentityCardRepository identityCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentityCardMockMvc;

    private IdentityCard identityCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentityCard createEntity(EntityManager em) {
        IdentityCard identityCard = new IdentityCard()
            .cardID(DEFAULT_CARD_ID)
            .name(DEFAULT_NAME)
            .dob(DEFAULT_DOB)
            .home(DEFAULT_HOME)
            .address(DEFAULT_ADDRESS)
            .sex(DEFAULT_SEX)
            .nationality(DEFAULT_NATIONALITY)
            .doe(DEFAULT_DOE)
            .photo(DEFAULT_PHOTO);
        return identityCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentityCard createUpdatedEntity(EntityManager em) {
        IdentityCard identityCard = new IdentityCard()
            .cardID(UPDATED_CARD_ID)
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .home(UPDATED_HOME)
            .address(UPDATED_ADDRESS)
            .sex(UPDATED_SEX)
            .nationality(UPDATED_NATIONALITY)
            .doe(UPDATED_DOE)
            .photo(UPDATED_PHOTO);
        return identityCard;
    }

    @BeforeEach
    public void initTest() {
        identityCard = createEntity(em);
    }

    @Test
    @Transactional
    void createIdentityCard() throws Exception {
        int databaseSizeBeforeCreate = identityCardRepository.findAll().size();
        // Create the IdentityCard
        restIdentityCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identityCard)))
            .andExpect(status().isCreated());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeCreate + 1);
        IdentityCard testIdentityCard = identityCardList.get(identityCardList.size() - 1);
        assertThat(testIdentityCard.getCardID()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testIdentityCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIdentityCard.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testIdentityCard.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testIdentityCard.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testIdentityCard.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testIdentityCard.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testIdentityCard.getDoe()).isEqualTo(DEFAULT_DOE);
        assertThat(testIdentityCard.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void createIdentityCardWithExistingId() throws Exception {
        // Create the IdentityCard with an existing ID
        identityCard.setId(1L);

        int databaseSizeBeforeCreate = identityCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identityCard)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdentityCards() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        // Get all the identityCardList
        restIdentityCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identityCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardID").value(hasItem(DEFAULT_CARD_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB)))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].doe").value(hasItem(DEFAULT_DOE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getIdentityCard() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        // Get the identityCard
        restIdentityCardMockMvc
            .perform(get(ENTITY_API_URL_ID, identityCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identityCard.getId().intValue()))
            .andExpect(jsonPath("$.cardID").value(DEFAULT_CARD_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.doe").value(DEFAULT_DOE))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO));
    }

    @Test
    @Transactional
    void getNonExistingIdentityCard() throws Exception {
        // Get the identityCard
        restIdentityCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdentityCard() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();

        // Update the identityCard
        IdentityCard updatedIdentityCard = identityCardRepository.findById(identityCard.getId()).get();
        // Disconnect from session so that the updates on updatedIdentityCard are not directly saved in db
        em.detach(updatedIdentityCard);
        updatedIdentityCard
            .cardID(UPDATED_CARD_ID)
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .home(UPDATED_HOME)
            .address(UPDATED_ADDRESS)
            .sex(UPDATED_SEX)
            .nationality(UPDATED_NATIONALITY)
            .doe(UPDATED_DOE)
            .photo(UPDATED_PHOTO);

        restIdentityCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdentityCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIdentityCard))
            )
            .andExpect(status().isOk());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
        IdentityCard testIdentityCard = identityCardList.get(identityCardList.size() - 1);
        assertThat(testIdentityCard.getCardID()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testIdentityCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIdentityCard.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testIdentityCard.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testIdentityCard.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIdentityCard.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testIdentityCard.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIdentityCard.getDoe()).isEqualTo(UPDATED_DOE);
        assertThat(testIdentityCard.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void putNonExistingIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, identityCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identityCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identityCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identityCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdentityCardWithPatch() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();

        // Update the identityCard using partial update
        IdentityCard partialUpdatedIdentityCard = new IdentityCard();
        partialUpdatedIdentityCard.setId(identityCard.getId());

        partialUpdatedIdentityCard.dob(UPDATED_DOB).home(UPDATED_HOME).nationality(UPDATED_NATIONALITY).doe(UPDATED_DOE);

        restIdentityCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentityCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentityCard))
            )
            .andExpect(status().isOk());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
        IdentityCard testIdentityCard = identityCardList.get(identityCardList.size() - 1);
        assertThat(testIdentityCard.getCardID()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testIdentityCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIdentityCard.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testIdentityCard.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testIdentityCard.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testIdentityCard.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testIdentityCard.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIdentityCard.getDoe()).isEqualTo(UPDATED_DOE);
        assertThat(testIdentityCard.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void fullUpdateIdentityCardWithPatch() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();

        // Update the identityCard using partial update
        IdentityCard partialUpdatedIdentityCard = new IdentityCard();
        partialUpdatedIdentityCard.setId(identityCard.getId());

        partialUpdatedIdentityCard
            .cardID(UPDATED_CARD_ID)
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .home(UPDATED_HOME)
            .address(UPDATED_ADDRESS)
            .sex(UPDATED_SEX)
            .nationality(UPDATED_NATIONALITY)
            .doe(UPDATED_DOE)
            .photo(UPDATED_PHOTO);

        restIdentityCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentityCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentityCard))
            )
            .andExpect(status().isOk());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
        IdentityCard testIdentityCard = identityCardList.get(identityCardList.size() - 1);
        assertThat(testIdentityCard.getCardID()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testIdentityCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIdentityCard.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testIdentityCard.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testIdentityCard.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIdentityCard.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testIdentityCard.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIdentityCard.getDoe()).isEqualTo(UPDATED_DOE);
        assertThat(testIdentityCard.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void patchNonExistingIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, identityCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identityCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identityCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdentityCard() throws Exception {
        int databaseSizeBeforeUpdate = identityCardRepository.findAll().size();
        identityCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(identityCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IdentityCard in the database
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdentityCard() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        int databaseSizeBeforeDelete = identityCardRepository.findAll().size();

        // Delete the identityCard
        restIdentityCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, identityCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdentityCard> identityCardList = identityCardRepository.findAll();
        assertThat(identityCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
