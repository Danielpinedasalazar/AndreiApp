package com.andreiapp.andreiapp.controller;

import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.dtos.VictimDTO;
import com.andreiapp.andreiapp.services.VictimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/victim")
@RequiredArgsConstructor
public class VictimController {

    private final VictimService victimService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('DAEMON')")
    public ResponseEntity<Response<?>> createVictim(@RequestBody VictimDTO victimDTO) {
        return ResponseEntity.ok(victimService.createVictim(victimDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DAEMON', 'SUPER_ADMIN')")
    public ResponseEntity<Response<VictimDTO>> getVictimById(@PathVariable ("id") Long id) {
        return ResponseEntity.ok(victimService.getVictimById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('DAEMON', 'SUPER_ADMIN')")
    public ResponseEntity<Response<List<VictimDTO>>> getAllVictims() {
        return ResponseEntity.ok(victimService.getAllVictims());
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('DAEMON', 'SUPER_ADMIN')")
    public ResponseEntity<Response<?>> deleteVictim(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        return ResponseEntity.ok(victimService.deleteVictim(code));
    }

    @GetMapping("/my-victims")
    @PreAuthorize("hasAnyAuthority('DAEMON')")
    public ResponseEntity<Response<List<VictimDTO>>> getMyVictims() {
        return ResponseEntity.ok(victimService.getMyVictims());
    }
}
