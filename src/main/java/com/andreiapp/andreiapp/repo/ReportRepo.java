package com.andreiapp.andreiapp.repo;

import com.andreiapp.andreiapp.entities.Report;
import com.andreiapp.andreiapp.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Long> {

    List<Report> findByCreatedByIdOrderByCreatedAtDesc(Long userId);

    List<Report> findByReviewedByIdOrderByCreatedAtDesc(Long reviewerId);

    List<Report> findByStatusOrderByCreatedAtDesc(ReportStatus status);

}
