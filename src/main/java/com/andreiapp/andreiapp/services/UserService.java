package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.UserDTO;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User currentUser();
    Optional<User> currentUserIfPresent();

    Response<?> updateMyAccount(UserDTO userDTO);

    Response<UserDTO> updateUserById(Long id,UserDTO userDTO);

    Response<List<UserDTO>> findAllUsers();

    Response<?> deleteUser(String email);

    Response<List<UserDTO>> findAllUsersByRole(Role role);
}
