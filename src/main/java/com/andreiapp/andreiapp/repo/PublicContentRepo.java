package com.andreiapp.andreiapp.repo;

import com.andreiapp.andreiapp.dtos.PublicContentDTO;
import com.andreiapp.andreiapp.entities.PublicContent;
import com.andreiapp.andreiapp.enums.PublicContentType;
import com.andreiapp.andreiapp.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicContentRepo extends JpaRepository<PublicContent, Long> {

    List<PublicContent> findByApprovedTrueOrderByCreatedAtDesc();

    List<PublicContent> findByApprovedFalseOrderByCreatedAtDesc();
}
