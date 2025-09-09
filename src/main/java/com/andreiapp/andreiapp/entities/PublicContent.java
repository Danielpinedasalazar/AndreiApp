package com.andreiapp.andreiapp.entities;

import com.andreiapp.andreiapp.enums.PublicContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "public_content")
@AllArgsConstructor
@NoArgsConstructor
public class PublicContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PublicContentType type;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String bodyMd;

    private String imageUrl;

    private String altText;

    @ManyToOne
    @JoinColumn(name = "submitted_by_user_id")
    private User submittedBy;

    private boolean approved;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
