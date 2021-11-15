package com.chipviet.project.repository;

import com.chipviet.project.domain.Connection;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Connection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    @Query("select connection from Connection connection where connection.user.login = ?#{principal.username}")
    List<Connection> findByUserIsCurrentUser();
}
