package com.andreiapp.andreiapp.entities;

import com.andreiapp.andreiapp.enums.ReportSeverity;
import com.andreiapp.andreiapp.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reports")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean anonymous;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    private String ipHash;

    @Column(nullable = false )
    private String title;

    @Column(nullable = false )
    private String description;

    @Enumerated(EnumType.STRING)
    private ReportSeverity severity;

    @ManyToOne
    @JoinColumn(name = "victim_id")
    private Victim victim;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @ManyToOne
    @JoinColumn(name = "reviewed_by_user_id")
    private User reviewedBy;

    @Column(columnDefinition = "text")
    private String reviewNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
