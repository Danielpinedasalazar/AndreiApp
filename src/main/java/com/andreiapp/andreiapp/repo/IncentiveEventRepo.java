package com.andreiapp.andreiapp.repo;

import com.andreiapp.andreiapp.entities.IncentiveEvent;
import com.andreiapp.andreiapp.enums.IncentiveType;
import com.andreiapp.andreiapp.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncentiveEventRepo extends JpaRepository<IncentiveEvent, Long> {

    List<IncentiveEvent> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<IncentiveEvent> findByTypeOrderByCreatedAtDesc(IncentiveType type);

    @Query("select coalesce(sum(i.points),0) from IncentiveEvent i where i.user.id = :userId")
    int sumPointsByUserId(Long userId);
}
