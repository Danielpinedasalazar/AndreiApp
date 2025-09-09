package com.andreiapp.andreiapp.services;

import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.UserDTO;
import com.andreiapp.andreiapp.dtos.VictimDTO;

import java.util.List;

public interface VictimService {

    Response<?> createVictim(VictimDTO victimDTO);

    Response<VictimDTO> getVictimById(Long id);

    Response<List<VictimDTO>> getAllVictims();

    Response<?> updateVictim(VictimDTO victimDTO);

    Response<?> deleteVictim(String code);

    Response<List<VictimDTO>> getMyVictims();
}
