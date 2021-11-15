package com.chipviet.project.web.rest;

import com.chipviet.project.domain.IdentityCard;
import com.chipviet.project.repository.IdentityCardRepository;
import com.chipviet.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.chipviet.project.domain.IdentityCard}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IdentityCardResource {

    private final Logger log = LoggerFactory.getLogger(IdentityCardResource.class);

    private static final String ENTITY_NAME = "identityCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentityCardRepository identityCardRepository;

    public IdentityCardResource(IdentityCardRepository identityCardRepository) {
        this.identityCardRepository = identityCardRepository;
    }

    /**
     * {@code POST  /identity-cards} : Create a new identityCard.
     *
     * @param identityCard the identityCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identityCard, or with status {@code 400 (Bad Request)} if the identityCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/identity-cards")
    public ResponseEntity<IdentityCard> createIdentityCard(@RequestBody IdentityCard identityCard) throws URISyntaxException {
        log.debug("REST request to save IdentityCard : {}", identityCard);
        if (identityCard.getId() != null) {
            throw new BadRequestAlertException("A new identityCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdentityCard result = identityCardRepository.save(identityCard);
        return ResponseEntity
            .created(new URI("/api/identity-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /identity-cards/:id} : Updates an existing identityCard.
     *
     * @param id the id of the identityCard to save.
     * @param identityCard the identityCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identityCard,
     * or with status {@code 400 (Bad Request)} if the identityCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identityCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/identity-cards/{id}")
    public ResponseEntity<IdentityCard> updateIdentityCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IdentityCard identityCard
    ) throws URISyntaxException {
        log.debug("REST request to update IdentityCard : {}, {}", id, identityCard);
        if (identityCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identityCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identityCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IdentityCard result = identityCardRepository.save(identityCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, identityCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /identity-cards/:id} : Partial updates given fields of an existing identityCard, field will ignore if it is null
     *
     * @param id the id of the identityCard to save.
     * @param identityCard the identityCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identityCard,
     * or with status {@code 400 (Bad Request)} if the identityCard is not valid,
     * or with status {@code 404 (Not Found)} if the identityCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the identityCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/identity-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IdentityCard> partialUpdateIdentityCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IdentityCard identityCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update IdentityCard partially : {}, {}", id, identityCard);
        if (identityCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identityCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identityCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IdentityCard> result = identityCardRepository
            .findById(identityCard.getId())
            .map(existingIdentityCard -> {
                if (identityCard.getCardID() != null) {
                    existingIdentityCard.setCardID(identityCard.getCardID());
                }
                if (identityCard.getName() != null) {
                    existingIdentityCard.setName(identityCard.getName());
                }
                if (identityCard.getDob() != null) {
                    existingIdentityCard.setDob(identityCard.getDob());
                }
                if (identityCard.getHome() != null) {
                    existingIdentityCard.setHome(identityCard.getHome());
                }
                if (identityCard.getAddress() != null) {
                    existingIdentityCard.setAddress(identityCard.getAddress());
                }
                if (identityCard.getSex() != null) {
                    existingIdentityCard.setSex(identityCard.getSex());
                }
                if (identityCard.getNationality() != null) {
                    existingIdentityCard.setNationality(identityCard.getNationality());
                }
                if (identityCard.getDoe() != null) {
                    existingIdentityCard.setDoe(identityCard.getDoe());
                }
                if (identityCard.getPhoto() != null) {
                    existingIdentityCard.setPhoto(identityCard.getPhoto());
                }

                return existingIdentityCard;
            })
            .map(identityCardRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, identityCard.getId().toString())
        );
    }

    /**
     * {@code GET  /identity-cards} : get all the identityCards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identityCards in body.
     */
    @GetMapping("/identity-cards")
    public ResponseEntity<List<IdentityCard>> getAllIdentityCards(Pageable pageable) {
        log.debug("REST request to get a page of IdentityCards");
        Page<IdentityCard> page = identityCardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /identity-cards/:id} : get the "id" identityCard.
     *
     * @param id the id of the identityCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identityCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/identity-cards/{id}")
    public ResponseEntity<IdentityCard> getIdentityCard(@PathVariable Long id) {
        log.debug("REST request to get IdentityCard : {}", id);
        Optional<IdentityCard> identityCard = identityCardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identityCard);
    }

    /**
     * {@code DELETE  /identity-cards/:id} : delete the "id" identityCard.
     *
     * @param id the id of the identityCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/identity-cards/{id}")
    public ResponseEntity<Void> deleteIdentityCard(@PathVariable Long id) {
        log.debug("REST request to delete IdentityCard : {}", id);
        identityCardRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
