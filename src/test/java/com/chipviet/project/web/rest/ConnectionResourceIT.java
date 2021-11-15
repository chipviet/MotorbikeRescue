package com.chipviet.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chipviet.project.IntegrationTest;
import com.chipviet.project.domain.Connection;
import com.chipviet.project.domain.enumeration.ConnectionStatus;
import com.chipviet.project.repository.ConnectionRepository;
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
 * Integration tests for the {@link ConnectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConnectionResourceIT {

    private static final String DEFAULT_CREATE_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_AT = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final ConnectionStatus DEFAULT_STATUS = ConnectionStatus.APPROVE;
    private static final ConnectionStatus UPDATED_STATUS = ConnectionStatus.DECLINE;

    private static final String ENTITY_API_URL = "/api/connections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectionMockMvc;

    private Connection connection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connection createEntity(EntityManager em) {
        Connection connection = new Connection()
            .createAt(DEFAULT_CREATE_AT)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .status(DEFAULT_STATUS);
        return connection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connection createUpdatedEntity(EntityManager em) {
        Connection connection = new Connection()
            .createAt(UPDATED_CREATE_AT)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .status(UPDATED_STATUS);
        return connection;
    }

    @BeforeEach
    public void initTest() {
        connection = createEntity(em);
    }

    @Test
    @Transactional
    void createConnection() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();
        // Create the Connection
        restConnectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate + 1);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testConnection.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testConnection.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testConnection.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createConnectionWithExistingId() throws Exception {
        // Create the Connection with an existing ID
        connection.setId(1L);

        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConnections() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList
        restConnectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get the connection
        restConnectionMockMvc
            .perform(get(ENTITY_API_URL_ID, connection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connection.getId().intValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConnection() throws Exception {
        // Get the connection
        restConnectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection
        Connection updatedConnection = connectionRepository.findById(connection.getId()).get();
        // Disconnect from session so that the updates on updatedConnection are not directly saved in db
        em.detach(updatedConnection);
        updatedConnection.createAt(UPDATED_CREATE_AT).longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE).status(UPDATED_STATUS);

        restConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConnection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConnection))
            )
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testConnection.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testConnection.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testConnection.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, connection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(connection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(connection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConnectionWithPatch() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection using partial update
        Connection partialUpdatedConnection = new Connection();
        partialUpdatedConnection.setId(connection.getId());

        partialUpdatedConnection.createAt(UPDATED_CREATE_AT).longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE);

        restConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConnection))
            )
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testConnection.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testConnection.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testConnection.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateConnectionWithPatch() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection using partial update
        Connection partialUpdatedConnection = new Connection();
        partialUpdatedConnection.setId(connection.getId());

        partialUpdatedConnection.createAt(UPDATED_CREATE_AT).longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE).status(UPDATED_STATUS);

        restConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConnection))
            )
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testConnection.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testConnection.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testConnection.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, connection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(connection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(connection))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();
        connection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(connection))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        int databaseSizeBeforeDelete = connectionRepository.findAll().size();

        // Delete the connection
        restConnectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, connection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
