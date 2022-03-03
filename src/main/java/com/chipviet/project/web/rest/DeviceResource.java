package com.chipviet.project.web.rest;

import com.chipviet.project.domain.Device;
import com.chipviet.project.domain.User;
import com.chipviet.project.repository.DeviceRepository;
import com.chipviet.project.repository.UserRepository;
import com.chipviet.project.service.dto.AddUserToDeviceDTO;
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
 * REST controller for managing {@link com.chipviet.project.domain.Device}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

    private static final String ENTITY_NAME = "device";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceResource(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /devices} : Create a new device.
     *
     * @param device the device to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new device, or with status {@code 400 (Bad Request)} if the device has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);

        Device A = deviceRepository.findByDeviceUuid(device.getDeviceUuid(), device.getUsedBy());
        log.debug(" Device A : {}", A);
        if (A != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (device.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> existingUser = userRepository.findOneByPhoneNumber(device.getUsedBy().toLowerCase());
        device.setUser(existingUser.get());
        Device result = deviceRepository.save(device);
        return ResponseEntity
            .created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/devices/technician")
    public ResponseEntity<Device> createDeviceForTechnician(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);

        Device A = deviceRepository.findByDeviceUuid(device.getDeviceUuid(), device.getUsedBy());
        log.debug(" Device A : {}", A);
        if (A != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (device.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> existingUser = userRepository.findOneByLogin(device.getUsedBy().toLowerCase());
        device.setUser(existingUser.get());
        Device result = deviceRepository.save(device);
        return ResponseEntity
            .created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /devices} : Create a new device.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new device, or with status {@code 400 (Bad Request)} if the device has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devices/addUser")
    public ResponseEntity<Device> addUserDevice(@RequestBody AddUserToDeviceDTO payload) throws URISyntaxException {
        log.debug("REST request to add user to device : {}", payload);
        Device newDevice = deviceRepository.findByDeviceUuid(payload.getDeviceUuid(), payload.getUsedBy());
        newDevice.setUser(payload.getUser());
        log.debug("REST request to device : {}", newDevice);
        //        if (payload.getDeviceUuid() != null) {
        //            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        //        }
        Device result = deviceRepository.save(newDevice);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "")).body(result);
    }

    /**
     * {@code PUT  /devices/:id} : Updates an existing device.
     *
     * @param id the id of the device to save.
     * @param device the device to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated device,
     * or with status {@code 400 (Bad Request)} if the device is not valid,
     * or with status {@code 500 (Internal Server Error)} if the device couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devices/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable(value = "id", required = false) final Long id, @RequestBody Device device)
        throws URISyntaxException {
        log.debug("REST request to update Device : {}, {}", id, device);
        if (device.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, device.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Device result = deviceRepository.save(device);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, device.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /devices/:id} : Partial updates given fields of an existing device, field will ignore if it is null
     *
     * @param id the id of the device to save.
     * @param device the device to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated device,
     * or with status {@code 400 (Bad Request)} if the device is not valid,
     * or with status {@code 404 (Not Found)} if the device is not found,
     * or with status {@code 500 (Internal Server Error)} if the device couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Device> partialUpdateDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Device device
    ) throws URISyntaxException {
        log.debug("REST request to partial update Device partially : {}, {}", id, device);
        if (device.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, device.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Device> result = deviceRepository
            .findById(device.getId())
            .map(existingDevice -> {
                if (device.getName() != null) {
                    existingDevice.setName(device.getName());
                }
                if (device.getDeviceUuid() != null) {
                    existingDevice.setDeviceUuid(device.getDeviceUuid());
                }
                if (device.getPlatform() != null) {
                    existingDevice.setPlatform(device.getPlatform());
                }
                if (device.getVersion() != null) {
                    existingDevice.setVersion(device.getVersion());
                }

                return existingDevice;
            })
            .map(deviceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, device.getId().toString())
        );
    }

    /**
     * {@code GET  /devices} : get all the devices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devices in body.
     */
    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices(Pageable pageable) {
        log.debug("REST request to get a page of Devices");
        Page<Device> page = deviceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /devices/:id} : get the "id" device.
     *
     * @param id the id of the device to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the device, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        Optional<Device> device = deviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(device);
    }

    /**
     * {@code DELETE  /devices/:id} : delete the "id" device.
     *
     * @param id the id of the device to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
