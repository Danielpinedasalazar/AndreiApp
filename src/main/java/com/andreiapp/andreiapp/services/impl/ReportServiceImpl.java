package com.andreiapp.andreiapp.services.impl;

import com.andreiapp.andreiapp.dtos.ReportDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.entities.Report;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.entities.Victim;
import com.andreiapp.andreiapp.enums.ReportStatus;
import com.andreiapp.andreiapp.exceptions.BadRequestException;
import com.andreiapp.andreiapp.exceptions.NotFoundException;
import com.andreiapp.andreiapp.repo.ReportRepo;
import com.andreiapp.andreiapp.repo.UserRepo;
import com.andreiapp.andreiapp.repo.VictimRepo;
import com.andreiapp.andreiapp.services.ReportService;
import com.andreiapp.andreiapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final VictimRepo victimRepo;
    private final UserService userService;
    private final UserRepo userRepo;
    private final ReportRepo reportRepo;
    private final ModelMapper modelMapper;


    @Override
    public Response<?> createReport(ReportDTO reportDTO) {

        User creator = userService.currentUserIfPresent().orElse(null);

        if(reportDTO.getTitle() == null || reportDTO.getTitle().isEmpty()) {
            throw new BadRequestException("El titulo es obligatorio");
        }

        if(reportDTO.getDescription() == null || reportDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripcion es obligatoria");
        }

        Victim victim = null;
        if (reportDTO.getVictim() != null && reportDTO.getVictim().getCode() != null) {
            victim = victimRepo.findByCode(reportDTO.getVictim().getCode().trim())
                    .orElseThrow(() -> new NotFoundException("Victim not found with code: " + reportDTO.getVictim().getCode()));
        }

        Report report = new Report();
        report.setAnonymous(reportDTO.isAnonymous() || creator == null);
        report.setCreatedBy(creator);
        report.setIpHash(reportDTO.getIpHash());
        report.setTitle(reportDTO.getTitle().trim());
        report.setDescription(reportDTO.getDescription().trim());
        report.setSeverity(reportDTO.getSeverity());
        report.setVictim(victim);
        report.setStatus(ReportStatus.IN_REVIEW);
        report.setCreatedAt(LocalDateTime.now());

        Report saved = reportRepo.save(report);
        ReportDTO out = modelMapper.map(saved, ReportDTO.class);

        return Response.<ReportDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reporte creado con exito")
                .data(out)
                .build();
    }

    @Override
    public Response<List<ReportDTO>> getAllReports() {

        List<ReportDTO> reportDTOS = reportRepo.findAll()
                .stream()
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .toList();

        return Response.<List<ReportDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(reportDTOS.isEmpty() ? "No hay reportes" : "Reportes obtenidos con exito")
                .data(reportDTOS)
                .build();
    }

    @Override
    public Response<List<ReportDTO>> getMyReports() {

        User me = userService.currentUser();

        List<ReportDTO> reportDTOS = reportRepo.findByCreatedByIdOrderByCreatedAtDesc(me.getId())
                .stream()
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .toList();

        return Response.<List<ReportDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(reportDTOS.isEmpty() ? "No hay reportes" : "Reportes obtenidos con exito")
                .data(reportDTOS)
                .build();
    }

    @Override
    public Response<List<ReportDTO>> getReportsByStatus(ReportStatus status) {

        List<ReportDTO> reportDTOS = reportRepo.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .toList();

        return Response.<List<ReportDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(reportDTOS.isEmpty() ? "No hay reportes" : "Reportes obtenidos con exito")
                .data(reportDTOS)
                .build();
    }

    @Override
    public Response<ReportDTO> getReportById(Long id) {

        ReportDTO reportDTO = reportRepo.findById(id)
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .orElseThrow(() -> new NotFoundException("Reporte no encontrado"));

        return Response.<ReportDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reporte obtenido con exito")
                .data(reportDTO)
                .build();
    }

    @Override
    public Response<ReportDTO> updateReport(Long id, ReportDTO reportDTO) {

        Report updateReportStatus = reportRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Reporte no encontrado"));

        if(reportDTO.getStatus() != null){
            updateReportStatus.setStatus(reportDTO.getStatus());
        }

        updateReportStatus.setUpdatedAt(LocalDateTime.now());

        Report updated = reportRepo.save(updateReportStatus);
        ReportDTO updatedReportDTO = modelMapper.map(updated, ReportDTO.class);

        return Response.<ReportDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reporte actualizado con exito")
                .data(updatedReportDTO)
                .build();
    }
}
