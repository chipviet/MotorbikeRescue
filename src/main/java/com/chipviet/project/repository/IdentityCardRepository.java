package com.chipviet.project.repository;

import com.chipviet.project.domain.IdentityCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IdentityCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {}
