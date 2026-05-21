package org.example.censohyrule.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.censohyrule.domain.common.SpecimenMapper;
import org.example.censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.censohyrule.domain.dto.response.SpecimenResponse;
import org.example.censohyrule.domain.entity.Specimen;
import org.example.censohyrule.exceptions.ResourceNotFoundException;
import org.example.censohyrule.repository.SpecimenRepository;
import org.example.censohyrule.service.SpecimenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImp implements SpecimenService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    public SpecimenResponse createSpecimen(CreateSpecimenRequest req) {
        return specimenMapper.toDto(
                specimenRepository.save(specimenMapper.toEntityCreate(req))
        );
    }

    @Override
    public List<SpecimenResponse> getAllSpecimen() {
        List<Specimen> specimens = specimenRepository.findAll();
        if (specimens.isEmpty())
            throw new ResourceNotFoundException("No specimens are registered in Hyrule");
        return specimens.stream().map(specimenMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {
        return specimenMapper.toDto(specimenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specimen not found in Hyrule")));
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest req) {
        this.getSpecimenById(id);
        return specimenMapper.toDto(specimenRepository.save(specimenMapper.toEntityUpdate(req, id)));
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {
        SpecimenResponse existSpecimen = this.getSpecimenById(id);
        specimenRepository.deleteById(id);
        return existSpecimen;
    }
}
