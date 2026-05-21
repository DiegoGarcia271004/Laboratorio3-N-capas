package org.example.censohyrule.service;

import org.example.censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.censohyrule.domain.dto.response.SpecimenResponse;

import java.util.List;
import java.util.UUID;

public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest req);
    List<SpecimenResponse> getAllSpecimen();
    SpecimenResponse getSpecimenById(UUID id);
    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest req);
    SpecimenResponse deleteSpecimen(UUID id);

}
