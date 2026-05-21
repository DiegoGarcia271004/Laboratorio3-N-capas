package org.example.censohyrule.service;

import org.example.censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.censohyrule.domain.dto.response.PageableResponse;
import org.example.censohyrule.domain.dto.response.specimen.SpecimenResponse;

import java.util.List;
import java.util.UUID;

public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest req);
    PageableResponse<SpecimenResponse> getAllSpecimen(int page, int size, String sortBy, String sortOrder);
    SpecimenResponse getSpecimenById(UUID id);
    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest req);
    SpecimenResponse deleteSpecimen(UUID id);

}
