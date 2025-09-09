package com.andreiapp.andreiapp.dtos;

import com.andreiapp.andreiapp.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    private String token;

    private Role role;

}
