package com.andreiapp.andreiapp.services.impl;

import com.andreiapp.andreiapp.dtos.LoginRequest;
import com.andreiapp.andreiapp.dtos.LoginResponse;
import com.andreiapp.andreiapp.dtos.RegistrationRequest;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.enums.Role;
import com.andreiapp.andreiapp.exceptions.BadRequestException;
import com.andreiapp.andreiapp.repo.UserRepo;
import com.andreiapp.andreiapp.security.JwtUtils;
import com.andreiapp.andreiapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Dentro de Register()");
        if(userRepo.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("El email ya existe");
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRole(Role.AGENT);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());
        userToSave.setActive(true);

        userRepo.save(userToSave);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Usuario registrado exitosamente")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Dentro de Login()");

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Credenciales invalidas"));

        if(!user.isActive()){
            throw new BadRequestException("Usuario inactivo");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Password incorrecto");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        Role role = user.getRole();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRole(role);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login exitoso")
                .data(loginResponse)
                .build();
    }
}
