package com.andreiapp.andreiapp.services.impl;

import com.andreiapp.andreiapp.dtos.IncentiveEventDTO;
import com.andreiapp.andreiapp.dtos.PublicContentDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.entities.PublicContent;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.exceptions.BadRequestException;
import com.andreiapp.andreiapp.exceptions.NotFoundException;
import com.andreiapp.andreiapp.repo.IncentiveEventRepo;
import com.andreiapp.andreiapp.repo.PublicContentRepo;
import com.andreiapp.andreiapp.services.PublicContentService;
import com.andreiapp.andreiapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicContentServiceImpl implements PublicContentService {

    private final PublicContentRepo publicContentRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final IncentiveEventRepo incentiveEventRepo;


    @Override
    public Response<?> createIncentiveEvent(PublicContentDTO publicContentDTO) {

        if(publicContentDTO.getTitle() == null || publicContentDTO.getTitle().isEmpty()) {
            throw new BadRequestException("El titulo es obligatorio");
        }

        if(publicContentDTO.getBodyMd() == null || publicContentDTO.getBodyMd().isEmpty()) {
            throw new BadRequestException("El contenido es obligatorio");
        }

        User user = userService.currentUserIfPresent().orElse(null);

        PublicContent publicContent = new PublicContent();
        publicContent.setTitle(publicContentDTO.getTitle().trim());
        publicContent.setBodyMd(publicContentDTO.getBodyMd().trim());
        publicContent.setType(publicContentDTO.getType());
        publicContent.setApproved(false);
        publicContent.setSubmittedBy(user);
        publicContent.setCreatedAt(LocalDateTime.now());
        publicContent.setUpdatedAt(LocalDateTime.now());

        PublicContent savedPublicContent = publicContentRepo.save(publicContent);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Contenido público creado con éxito")
                .data(modelMapper.map(savedPublicContent, PublicContentDTO.class))
                .build();
    }

    @Override
    public Response<List<PublicContentDTO>> getApprovedPublicContent() {

        List<PublicContentDTO> incentiveEventDTOS = publicContentRepo.findByApprovedTrueOrderByCreatedAtDesc()
                .stream()
                .map(event -> modelMapper.map(event, PublicContentDTO.class))
                .toList();

        return Response.<List<PublicContentDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(incentiveEventDTOS.isEmpty() ? "No hay eventos de incentivos aprobados" : "Eventos de incentivos aprobados encontrados")
                .data(incentiveEventDTOS)
                .build();
    }

    @Override
    public Response<List<PublicContentDTO>> getApprovedFalsePublicContentById() {

        List<PublicContentDTO> publicContentDTOS = publicContentRepo.findByApprovedFalseOrderByCreatedAtDesc()
                .stream()
                .map(event -> modelMapper.map(event, PublicContentDTO.class))
                .toList();

        return Response.<List<PublicContentDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(publicContentDTOS.isEmpty() ? "No hay eventos de incentivos pendientes de aprobación" : "Eventos de incentivos pendientes de aprobación encontrados")
                .data(publicContentDTOS)
                .build();
    }

    @Override
    public Response<List<PublicContentDTO>> getAllEvents() {

        List<PublicContentDTO> publicContentDTOS = publicContentRepo.findAll()
                .stream()
                .map(content -> modelMapper.map(content, PublicContentDTO.class))
                .toList();

        return Response.<List<PublicContentDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(publicContentDTOS.isEmpty() ? "No hay eventos" : "Eventos encontrados")
                .data(publicContentDTOS)
                .build();
    }

    @Override
    public Response<PublicContentDTO> updateApprovedById(PublicContentDTO publicContentDTO, Long id) {

        PublicContent publicContent = publicContentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Contenido público no encontrado con id: " + id));

        publicContent.setApproved(publicContentDTO.isApproved());
        publicContent.setUpdatedAt(LocalDateTime.now());

        PublicContent updatedContent = publicContentRepo.save(publicContent);

        return Response.<PublicContentDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Contenido público actualizado con éxito")
                .data(modelMapper.map(updatedContent, PublicContentDTO.class))
                .build();
    }
}
