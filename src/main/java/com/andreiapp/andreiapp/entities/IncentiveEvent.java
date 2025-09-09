package com.andreiapp.andreiapp.entities;

import com.andreiapp.andreiapp.enums.IncentiveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "incentive_events")
@AllArgsConstructor
@NoArgsConstructor
public class IncentiveEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigned_by_user_id")
    private User assignedBy;

    @Enumerated(EnumType.STRING)
    private IncentiveType type;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private int points;

    private LocalDateTime createdAt;
}
