package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.IncentiveEventDTO;
import com.andreiapp.andreiapp.dtos.PublicContentDTO;
import com.andreiapp.andreiapp.dtos.Response;

import java.util.List;

public interface PublicContentService {

    Response<?> createIncentiveEvent(PublicContentDTO publicContentDTO);

    Response<List<PublicContentDTO>> getApprovedPublicContent();

    Response<List<PublicContentDTO>> getApprovedFalsePublicContentById();

    Response<List<PublicContentDTO>> getAllEvents();

    Response<PublicContentDTO> updateApprovedById(PublicContentDTO publicContentDTO, Long id);
}
