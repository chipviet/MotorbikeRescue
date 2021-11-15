package com.chipviet.project.repository;

import com.chipviet.project.domain.RescueTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RescueTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RescueTeamRepository extends JpaRepository<RescueTeam, Long> {}
