package com.andreiapp.andreiapp.repo;

import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

}
