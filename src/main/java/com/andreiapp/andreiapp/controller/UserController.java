package com.andreiapp.andreiapp.controller;

import com.andreiapp.andreiapp.dtos.LoginRequest;
import com.andreiapp.andreiapp.dtos.RegistrationRequest;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.UserDTO;
import com.andreiapp.andreiapp.enums.Role;
import com.andreiapp.andreiapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<Response<?>> updateMyAccount(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateMyAccount(userDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<UserDTO>> updateUserById(@PathVariable ("id") Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserById(id, userDTO));
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<List<UserDTO>>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/delete")
    public ResponseEntity<Response<?>> deleteUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return ResponseEntity.ok(userService.deleteUser(email));
    }

    @GetMapping("/find-by-role")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<List<UserDTO>>> findAllUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(userService.findAllUsersByRole(role));
    }

    @PostMapping("/create-daemon")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<?>> createUserWithRoleDaemon(@RequestBody UserDTO userDTO  ) {
        return ResponseEntity.ok(userService.createUserWithRoleDaemon(userDTO));
    }



}
