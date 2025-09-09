package com.andreiapp.andreiapp.dtos;


import com.andreiapp.andreiapp.enums.IncentiveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncentiveEventDTO {

    private UserDTO user;

    private UserDTO assignedBy;

    private IncentiveType type;

    private String name;

    private String description;

    private int points;

    private LocalDateTime createdAt;

}
