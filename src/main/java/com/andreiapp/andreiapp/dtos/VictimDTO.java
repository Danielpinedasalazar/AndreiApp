package com.andreiapp.andreiapp.dtos;

import com.andreiapp.andreiapp.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimDTO {

    private String code;

    private String alias;

    private String notes;

    private RiskLevel riskLevel;

    private boolean active;

    private UserDTO createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
