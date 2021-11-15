package com.chipviet.project.web.rest;

import com.chipviet.project.domain.RescueTeam;
import com.chipviet.project.repository.RescueTeamRepository;
import com.chipviet.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.chipviet.project.domain.RescueTeam}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RescueTeamResource {

    private final Logger log = LoggerFactory.getLogger(RescueTeamResource.class);

    private static final String ENTITY_NAME = "rescueTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RescueTeamRepository rescueTeamRepository;

    public RescueTeamResource(RescueTeamRepository rescueTeamRepository) {
        this.rescueTeamRepository = rescueTeamRepository;
    }

    /**
     * {@code POST  /rescue-teams} : Create a new rescueTeam.
     *
     * @param rescueTeam the rescueTeam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rescueTeam, or with status {@code 400 (Bad Request)} if the rescueTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rescue-teams")
    public ResponseEntity<RescueTeam> createRescueTeam(@RequestBody RescueTeam rescueTeam) throws URISyntaxException {
        log.debug("REST request to save RescueTeam : {}", rescueTeam);
        if (rescueTeam.getId() != null) {
            throw new BadRequestAlertException("A new rescueTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RescueTeam result = rescueTeamRepository.save(rescueTeam);
        return ResponseEntity
            .created(new URI("/api/rescue-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rescue-teams/:id} : Updates an existing rescueTeam.
     *
     * @param id the id of the rescueTeam to save.
     * @param rescueTeam the rescueTeam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rescueTeam,
     * or with status {@code 400 (Bad Request)} if the rescueTeam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rescueTeam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rescue-teams/{id}")
    public ResponseEntity<RescueTeam> updateRescueTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RescueTeam rescueTeam
    ) throws URISyntaxException {
        log.debug("REST request to update RescueTeam : {}, {}", id, rescueTeam);
        if (rescueTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rescueTeam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rescueTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RescueTeam result = rescueTeamRepository.save(rescueTeam);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rescueTeam.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rescue-teams/:id} : Partial updates given fields of an existing rescueTeam, field will ignore if it is null
     *
     * @param id the id of the rescueTeam to save.
     * @param rescueTeam the rescueTeam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rescueTeam,
     * or with status {@code 400 (Bad Request)} if the rescueTeam is not valid,
     * or with status {@code 404 (Not Found)} if the rescueTeam is not found,
     * or with status {@code 500 (Internal Server Error)} if the rescueTeam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rescue-teams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RescueTeam> partialUpdateRescueTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RescueTeam rescueTeam
    ) throws URISyntaxException {
        log.debug("REST request to partial update RescueTeam partially : {}, {}", id, rescueTeam);
        if (rescueTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rescueTeam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rescueTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RescueTeam> result = rescueTeamRepository
            .findById(rescueTeam.getId())
            .map(existingRescueTeam -> {
                if (rescueTeam.getName() != null) {
                    existingRescueTeam.setName(rescueTeam.getName());
                }
                if (rescueTeam.getPhoneNumber() != null) {
                    existingRescueTeam.setPhoneNumber(rescueTeam.getPhoneNumber());
                }
                if (rescueTeam.getLongitude() != null) {
                    existingRescueTeam.setLongitude(rescueTeam.getLongitude());
                }
                if (rescueTeam.getLatitude() != null) {
                    existingRescueTeam.setLatitude(rescueTeam.getLatitude());
                }
                if (rescueTeam.getAddress() != null) {
                    existingRescueTeam.setAddress(rescueTeam.getAddress());
                }
                if (rescueTeam.getCreateAt() != null) {
                    existingRescueTeam.setCreateAt(rescueTeam.getCreateAt());
                }

                return existingRescueTeam;
            })
            .map(rescueTeamRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rescueTeam.getId().toString())
        );
    }

    /**
     * {@code GET  /rescue-teams} : get all the rescueTeams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rescueTeams in body.
     */
    @GetMapping("/rescue-teams")
    public List<RescueTeam> getAllRescueTeams() {
        log.debug("REST request to get all RescueTeams");
        return rescueTeamRepository.findAll();
    }

    /**
     * {@code GET  /rescue-teams/:id} : get the "id" rescueTeam.
     *
     * @param id the id of the rescueTeam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rescueTeam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rescue-teams/{id}")
    public ResponseEntity<RescueTeam> getRescueTeam(@PathVariable Long id) {
        log.debug("REST request to get RescueTeam : {}", id);
        Optional<RescueTeam> rescueTeam = rescueTeamRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rescueTeam);
    }

    /**
     * {@code DELETE  /rescue-teams/:id} : delete the "id" rescueTeam.
     *
     * @param id the id of the rescueTeam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rescue-teams/{id}")
    public ResponseEntity<Void> deleteRescueTeam(@PathVariable Long id) {
        log.debug("REST request to delete RescueTeam : {}", id);
        rescueTeamRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
