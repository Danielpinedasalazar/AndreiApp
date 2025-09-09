package com.andreiapp.andreiapp.dtos;

import com.andreiapp.andreiapp.enums.ReportSeverity;
import com.andreiapp.andreiapp.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private Long id;

    private boolean anonymous;

    private UserDTO createdBy;

    private String ipHash;

    private String title;

    private String description;

    private ReportSeverity severity;

    private VictimDTO victim;

    private ReportStatus status;

    private UserDTO reviewedBy;

    private String reviewNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
