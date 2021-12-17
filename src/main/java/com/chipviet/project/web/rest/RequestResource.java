package com.chipviet.project.web.rest;

import com.chipviet.project.domain.Device;
import com.chipviet.project.domain.Request;
import com.chipviet.project.domain.User;
import com.chipviet.project.repository.DeviceRepository;
import com.chipviet.project.repository.RequestRepository;
import com.chipviet.project.repository.UserRepository;
import com.chipviet.project.service.PushNotificationService;
import com.chipviet.project.web.rest.errors.BadRequestAlertException;
import java.lang.reflect.Array;
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
 * REST controller for managing {@link com.chipviet.project.domain.Request}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestRepository requestRepository;
    private final DeviceRepository deviceRepository;

    private final UserRepository userRepository;

    public RequestResource(RequestRepository requestRepository, UserRepository userRepository, DeviceRepository deviceRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param request the request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new request, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requests")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to save Request : {}", request);
        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Request result = requestRepository.save(request);
        List<User> listRepairer = userRepository.findRepairerNearest(request.getLatitude(), request.getLongitude());
        log.debug("result: {}", listRepairer);
        log.debug("request.getUser: {}", request.getUser().getLogin());
        for (int i = 0; i < listRepairer.toArray().length; i++) {
            User userObj = (User) Array.get(listRepairer.toArray(), i);
            Optional<User> user = userRepository.findOneByLogin(userObj.getLogin());
            log.debug("userObj.getLogin()  : {}", userObj.getLogin());
            log.debug("request.getUser().getLogin()  : {}", request.getUser().getLogin());
            if (!userObj.getLogin().equals(request.getUser().getLogin())) {
                List<Device> devices = deviceRepository.findByUserObject(user);
                log.debug("userObj.getPhoneNumber() : {}", userObj.getPhoneNumber());
                try {
                    PushNotificationService.sendMessageToUser(
                        "There is someone near you who is having problems with their motorbike. Would you agree to help them?",
                        devices,
                        user
                    );
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        return ResponseEntity
            .created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param request the request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new request, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confirm")
    public ResponseEntity<Request> createConfirm(@RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to save Request : {}", request);
        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Request result = requestRepository.save(request);
        List<User> listRepairer = userRepository.findRepairerNearest(request.getLatitude(), request.getLongitude());
        log.debug("result: {}", listRepairer);
        log.debug("request.getUser: {}", request.getUser().getLogin());
        for (int i = 0; i < listRepairer.toArray().length; i++) {
            User userObj = (User) Array.get(listRepairer.toArray(), i);
            Optional<User> user = userRepository.findOneByLogin(userObj.getLogin());
            if (userObj.getLogin() != request.getUser().getLogin()) {
                List<Device> devices = deviceRepository.findByUserObject(user);
                log.debug("userObj.getPhoneNumber() : {}", userObj.getPhoneNumber());
                try {
                    PushNotificationService.sendMessageToUser(
                        "There is someone near you who is having problems with their motorbike. Would you agree to help them?",
                        devices,
                        user
                    );
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return ResponseEntity
            .created(new URI("/api/confirm/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requests/:id} : Updates an existing request.
     *
     * @param id the id of the request to save.
     * @param request the request to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated request,
     * or with status {@code 400 (Bad Request)} if the request is not valid,
     * or with status {@code 500 (Internal Server Error)} if the request couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requests/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable(value = "id", required = false) final Long id, @RequestBody Request request)
        throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, request);
        if (request.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, request.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Request result = requestRepository.save(request);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, request.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requests/:id} : Partial updates given fields of an existing request, field will ignore if it is null
     *
     * @param id the id of the request to save.
     * @param request the request to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated request,
     * or with status {@code 400 (Bad Request)} if the request is not valid,
     * or with status {@code 404 (Not Found)} if the request is not found,
     * or with status {@code 500 (Internal Server Error)} if the request couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Request> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Request request
    ) throws URISyntaxException {
        log.debug("REST request to partial update Request partially : {}, {}", id, request);
        if (request.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, request.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Request> result = requestRepository
            .findById(request.getId())
            .map(existingRequest -> {
                if (request.getLongitude() != null) {
                    existingRequest.setLongitude(request.getLongitude());
                }
                if (request.getLatitude() != null) {
                    existingRequest.setLatitude(request.getLatitude());
                }
                if (request.getCreateAt() != null) {
                    existingRequest.setCreateAt(request.getCreateAt());
                }
                if (request.getMessage() != null) {
                    existingRequest.setMessage(request.getMessage());
                }
                if (request.getStatus() != null) {
                    existingRequest.setStatus(request.getStatus());
                }

                return existingRequest;
            })
            .map(requestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, request.getId().toString())
        );
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("/requests")
    public List<Request> getAllRequests() {
        log.debug("REST request to get all Requests");
        return requestRepository.findAll();
    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the request to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the request, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<Request> request = requestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(request);
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the request to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
