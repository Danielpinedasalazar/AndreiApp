package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.LoginRequest;
import com.andreiapp.andreiapp.dtos.LoginResponse;
import com.andreiapp.andreiapp.dtos.RegistrationRequest;
import com.andreiapp.andreiapp.dtos.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
