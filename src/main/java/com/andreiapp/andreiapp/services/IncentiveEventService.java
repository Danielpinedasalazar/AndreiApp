package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.IncentiveEventDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.enums.IncentiveType;

import java.util.List;

public interface IncentiveEventService {

    Response<?> createIncentiveEvent(IncentiveEventDTO incentiveEventDTO);

    Response<List<IncentiveEventDTO>> getAllIncentiveEvents();

    Response<Integer> getUserBalance(Long id);

    Response<List<IncentiveEventDTO>> getAllByType(IncentiveType type);

    Response<List<IncentiveEventDTO>> getUserEvents(Long id);
}
