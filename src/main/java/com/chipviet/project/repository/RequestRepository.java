package com.chipviet.project.repository;

import com.chipviet.project.domain.Request;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select request from Request request where request.user.login = ?#{principal.username}")
    List<Request> findByUserIsCurrentUser();
}
