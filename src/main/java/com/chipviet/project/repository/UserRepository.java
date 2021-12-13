package com.chipviet.project.repository;

import com.chipviet.project.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByPhoneNumber(String phoneNumber);

    @Query(
        value = "SELECT * FROM (" +
        "    SELECT *, " +
        "        (" +
        "            (" +
        "                (" +
        "                    acos(" +
        "                        sin(( :latitude * pi() / 180))" +
        "                        *" +
        "                        sin(( latitude * pi() / 180)) + cos((:latitude * pi() /180 ))" +
        "                        *" +
        "                        cos(( latitude * pi() / 180)) * cos((( :longitude - longitude) * pi()/180)))" +
        "                ) * 180/pi()" +
        "            ) * 60 * 1.1515 * 1.609344" +
        "        )" +
        "    as distance FROM jhi_user" +
        ") User" +
        " WHERE distance < 3",
        nativeQuery = true
    )
    List<User> findRepairerNearest(@Param("latitude") String latitude, @Param("longitude") String longitude);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
