package com.andreiapp.andreiapp.controller;

import com.andreiapp.andreiapp.dtos.ReportDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.enums.ReportStatus;
import com.andreiapp.andreiapp.services.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<Response<?>> createReport(@RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.createReport(reportDTO));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('DAEMON', 'SUPER_ADMIN')")
    public ResponseEntity<Response<?>>getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/my-reports")
    @PreAuthorize("hasAuthority('DAEMON')")
    public ResponseEntity<Response<List<ReportDTO>>> getMyReports() {
        return ResponseEntity.ok(reportService.getMyReports());
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('DAEMON')")
    public ResponseEntity<Response<List<ReportDTO>>> getReportsByStatus(@RequestBody Map<String, String> body) {
        ReportStatus status = ReportStatus.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(reportService.getReportsByStatus(status));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DAEMON')")
    public ResponseEntity<Response<ReportDTO>> getReportById(@PathVariable ("id") Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @PutMapping("/update-status/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<ReportDTO>> updateReport(@PathVariable ("id") Long id, @RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.updateReport(id, reportDTO));
    }
}
