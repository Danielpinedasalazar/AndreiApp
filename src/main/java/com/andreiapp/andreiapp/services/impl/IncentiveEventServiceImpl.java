package com.andreiapp.andreiapp.services.impl;

import com.andreiapp.andreiapp.dtos.IncentiveEventDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.UserDTO;
import com.andreiapp.andreiapp.entities.IncentiveEvent;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.enums.IncentiveType;
import com.andreiapp.andreiapp.exceptions.BadRequestException;
import com.andreiapp.andreiapp.repo.IncentiveEventRepo;
import com.andreiapp.andreiapp.repo.UserRepo;
import com.andreiapp.andreiapp.services.AuthService;
import com.andreiapp.andreiapp.services.IncentiveEventService;
import com.andreiapp.andreiapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncentiveEventServiceImpl implements IncentiveEventService {

    private final UserService userService;
    private final IncentiveEventRepo incentiveEventRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    @Override
    public Response<?> createIncentiveEvent(IncentiveEventDTO incentiveEventDTO) {

        User assignedBy = userService.currentUser();

        if (incentiveEventDTO.getUser() == null) {
            throw new BadRequestException("El beneficiario (user) es obligatorio");
        }
        if (incentiveEventDTO.getName() == null || incentiveEventDTO.getName().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio");
        }
        if (incentiveEventDTO.getDescription() == null || incentiveEventDTO.getDescription().isBlank()) {
            throw new BadRequestException("La descripción es obligatoria");
        }
        if (incentiveEventDTO.getType() == null) {
            throw new BadRequestException("El tipo de incentivo es obligatorio");
        }
        if (incentiveEventDTO.getPoints() == 0) {
            throw new BadRequestException("Los puntos no pueden ser 0");
        }

        UserDTO targetDTO = incentiveEventDTO.getUser();
        User beneficiary = null;

        if(targetDTO.getEmail() != null && !targetDTO.getEmail().isBlank()) {
            beneficiary = userRepo.findByEmail(targetDTO.getEmail().trim())
                    .orElseThrow(() -> new BadRequestException("No se encontró un usuario con el email: " + targetDTO.getEmail()));
        } else if (targetDTO.getId() != null) {
            beneficiary = userRepo.findById(targetDTO.getId())
                    .orElseThrow(() -> new BadRequestException("No se encontró un usuario con el ID: " + targetDTO.getId()));
        } else {
            throw new BadRequestException("El beneficiario (user) debe tener al menos un email o un ID");
        }

        IncentiveEvent entity = new IncentiveEvent();
        entity.setUser(beneficiary);
        entity.setAssignedBy(assignedBy);
        entity.setType(incentiveEventDTO.getType());
        entity.setName(incentiveEventDTO.getName().trim());
        entity.setDescription(incentiveEventDTO.getDescription().trim());
        entity.setPoints(incentiveEventDTO.getPoints());
        entity.setCreatedAt(LocalDateTime.now());

        IncentiveEvent saved = incentiveEventRepo.save(entity);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Incentivo creado con exito")
                .data(saved)
                .build();
    }

    @Override
    public Response<List<IncentiveEventDTO>> getAllIncentiveEvents() {

        List<IncentiveEventDTO> allEvents = incentiveEventRepo.findAll()
                .stream()
                .map(event -> modelMapper.map(event, IncentiveEventDTO.class))
                .toList();

        return Response.<List<IncentiveEventDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(allEvents.isEmpty() ? "No hay eventos de incentivos" : "Eventos de incentivos obtenidos con exito")
                .data(allEvents)
                .build();
    }

    @Override
    public Response<List<IncentiveEventDTO>> getAllByType(IncentiveType type) {

        List<IncentiveEventDTO> eventsByType = incentiveEventRepo.findByTypeOrderByCreatedAtDesc(type)
                .stream()
                .map(event -> modelMapper.map(event, IncentiveEventDTO.class))
                .toList();

        return Response.<List<IncentiveEventDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(eventsByType.isEmpty() ? "No hay eventos de incentivos de tipo " + type : "Eventos de incentivos obtenidos con exito")
                .data(eventsByType)
                .build();
    }

    @Override
    public Response<List<IncentiveEventDTO>> getUserEvents(Long id) {

        List<IncentiveEventDTO> userEvents = incentiveEventRepo.findByUserIdOrderByCreatedAtDesc(id)
                .stream()
                .map(event -> modelMapper.map(event, IncentiveEventDTO.class))
                .toList();

        return Response.<List<IncentiveEventDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(userEvents.isEmpty() ? "No hay eventos de incentivos para el usuario con ID " + id : "Eventos de incentivos obtenidos con exito")
                .data(userEvents)
                .build();
    }

    @Override
    public Response<Integer> getUserBalance(Long id) {

        int balance = incentiveEventRepo.sumPointsByUserId(id);
        return Response.<Integer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Balance retrieved successfully")
                .data(balance)
                .build();
    }
}
