package com.andreiapp.andreiapp.services.impl;

import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.VictimDTO;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.entities.Victim;
import com.andreiapp.andreiapp.exceptions.BadRequestException;
import com.andreiapp.andreiapp.exceptions.NotFoundException;
import com.andreiapp.andreiapp.repo.VictimRepo;
import com.andreiapp.andreiapp.services.UserService;
import com.andreiapp.andreiapp.services.VictimService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VictimServiceImpl implements VictimService {

    private final VictimRepo victimRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    public Response<?> createVictim(VictimDTO victimDTO) {

        if(victimDTO.getCode() == null || victimDTO.getCode().isBlank()) {
            throw new BadRequestException("Victim code is required");
        }

        if(victimDTO.getAlias() == null || victimDTO.getAlias().isBlank()) {
            throw new BadRequestException("Victim alias is required");
        }

        victimRepo.findByCode(victimDTO.getCode().trim()).ifPresent(victim -> {
            throw new BadRequestException("Victim code already exists");
        });

        User creator = userService.currentUser();

        Victim victimToSave = new Victim();
        victimToSave.setCode(victimDTO.getCode().trim());
        victimToSave.setAlias(victimDTO.getAlias().trim());
        victimToSave.setNotes(victimDTO.getNotes());
        victimToSave.setRiskLevel(victimDTO.getRiskLevel());
        victimToSave.setActive(true);
        victimToSave.setCreatedBy(creator);
        victimToSave.setCreatedAt(LocalDateTime.now());

        Victim savedVictim = victimRepo.save(victimToSave);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Victima creada con exito")
                .data(savedVictim.getId())
                .build();
    }

    @Override
    public Response<VictimDTO> getVictimById(Long id) {

        Victim victim = victimRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Victim not found"));

        VictimDTO victimDTO = modelMapper.map(victim, VictimDTO.class);

        return Response.<VictimDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Victim retrieved successfully")
                .data(victimDTO)
                .build();
    }

    @Override
    public Response<List<VictimDTO>> getAllVictims() {

        List<VictimDTO> victims = victimRepo.findAll().stream()
                .map(victim -> modelMapper.map(victim, VictimDTO.class))
                .toList();

        return Response.<List<VictimDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(victims.isEmpty() ? "No victims found" : "Victims retrieved successfully")
                .data(victims)
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateVictim(VictimDTO victimDTO) {
        Victim victim = victimRepo.findByCode(victimDTO.getCode().trim())
                .orElseThrow(() -> new NotFoundException("Victim not found"));

        if (victimDTO.getAlias() != null && !victimDTO.getAlias().isBlank()) {
            victim.setAlias(victimDTO.getAlias().trim());
        }
        if (victimDTO.getNotes() != null) {
            victim.setNotes(victimDTO.getNotes());
        }
        if (victimDTO.getRiskLevel() != null) {
            victim.setRiskLevel(victimDTO.getRiskLevel());
        }
        victim.setUpdatedAt(LocalDateTime.now());

        victimRepo.save(victim);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Victim updated successfully")
                .build();
    }

    @Override
    public Response<?> deleteVictim(String code) {

        Victim victim = victimRepo.findByCode(code.trim())
                .orElseThrow(() -> new NotFoundException("Victim not found"));

        victimRepo.delete(victim);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Victim deleted successfully")
                .build();
    }

    @Override
    public Response<List<VictimDTO>> getMyVictims() {

        User current = userService.currentUser();

        List<VictimDTO> victims = victimRepo.findByCreatedById(current.getId())
                .stream()
                .map(victim -> modelMapper.map(victim, VictimDTO.class))
                .toList();

        return Response.<List<VictimDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(victims.isEmpty() ? "No victims found" : "Victims retrieved successfully")
                .data(victims)
                .build();
    }
}
