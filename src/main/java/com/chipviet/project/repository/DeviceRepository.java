package com.chipviet.project.repository;

import com.chipviet.project.domain.Device;
import com.chipviet.project.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("select device from Device device where device.user.login = ?#{principal.username}")
    List<Device> findByUserIsCurrentUser();

    @Query("SELECT t from Device t where t.user = :user ")
    List<Device> findByUser(@Param("user") User user);

    @Query("SELECT t from Device t where t.user = :user ")
    List<Device> findByUserObject(@Param("user") Object user);

    @Query("SELECT t from Device t where t.deviceUuid = :deviceUuid and t.usedBy =:usedBy ")
    Device findByDeviceUuid(@Param("deviceUuid") String deviceUuid, @Param("usedBy") String usedBy);

    Optional<Device> findOneByDeviceUuid(String deviceUuid);
}
