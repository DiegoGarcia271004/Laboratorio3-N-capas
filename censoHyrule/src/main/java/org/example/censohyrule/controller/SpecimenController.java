package org.example.censohyrule.controller;

import lombok.RequiredArgsConstructor;
import org.example.censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.censohyrule.domain.dto.response.GeneralResponse;
import org.example.censohyrule.service.SpecimenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/specimen")
public class SpecimenController {

    private final SpecimenService specimenService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createSpecimen(@RequestBody CreateSpecimenRequest req) {
        return buildResponse(
                "Specimen created successfully",
                HttpStatus.CREATED,
                specimenService.createSpecimen(req)
        );
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<GeneralResponse> getSpecimenById(@PathVariable UUID id) {
        return buildResponse("Specimen found", HttpStatus.OK, specimenService.getSpecimenById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<GeneralResponse> getAllSpecimens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        return buildResponse(
                "Specimens found",
                HttpStatus.OK,
                specimenService.getAllSpecimen(page, size, sortBy, sortOrder)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> updateSpecimen(
            @PathVariable UUID id,
            @RequestBody UpdateSpecimenRequest req
            ) {
        return buildResponse("Specimen updated successfully",
                HttpStatus.OK,
                specimenService.updateSpecimen(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> deleteSpecimen(@PathVariable UUID id){
        return buildResponse(
                "Specimen deleted successfully",
                HttpStatus.OK,
                specimenService.deleteSpecimen(id)
        );
    }

    public ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}
