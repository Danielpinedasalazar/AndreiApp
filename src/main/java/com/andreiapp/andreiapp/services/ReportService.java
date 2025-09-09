package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.ReportDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.enums.ReportStatus;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    Response<?> createReport(ReportDTO reportDTO);

    Response<List<ReportDTO>> getAllReports();

    Response<List<ReportDTO>> getMyReports();

    Response<List<ReportDTO>> getReportsByStatus(ReportStatus status);

    Response<ReportDTO> getReportById(Long id);
}
