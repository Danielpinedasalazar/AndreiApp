package com.andreiapp.andreiapp.controller;

import com.andreiapp.andreiapp.dtos.IncentiveEventDTO;
import com.andreiapp.andreiapp.dtos.PublicContentDTO;
import com.andreiapp.andreiapp.dtos.Response;
import com.andreiapp.andreiapp.services.PublicContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class PublicContentController {

    private final PublicContentService publicContentService;

    @PostMapping("/create")
    public ResponseEntity<Response<?>> createIncentiveEvent(@RequestBody PublicContentDTO publicContentDTO) {
        return ResponseEntity.ok(publicContentService.createIncentiveEvent(publicContentDTO));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<List<PublicContentDTO>>> getAllEvents() {
        return ResponseEntity.ok(publicContentService.getAllEvents());
    }

    @GetMapping("/approved")
    public ResponseEntity<Response<List<PublicContentDTO>>> getApprovedPublicContent() {
        return ResponseEntity.ok(publicContentService.getApprovedPublicContent());
    }

    @GetMapping("/not-approved")
    public ResponseEntity<Response<List<PublicContentDTO>>> getApprovedFalsePublicContentById() {
        return ResponseEntity.ok(publicContentService.getApprovedFalsePublicContentById());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Response<PublicContentDTO>> updateApprovedById(@PathVariable ("id") Long id, @RequestBody PublicContentDTO publicContentDTO ) {
        return ResponseEntity.ok(publicContentService.updateApprovedById(publicContentDTO, id));
    }
}
