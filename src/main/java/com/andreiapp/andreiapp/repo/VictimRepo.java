package com.andreiapp.andreiapp.repo;

import com.andreiapp.andreiapp.entities.Victim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VictimRepo extends JpaRepository<Victim, Long> {

    Optional<Victim> findByCode(String code);

    List<Victim> findByCreatedById(Long userId);
}

