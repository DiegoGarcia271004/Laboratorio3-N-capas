package org.example.censohyrule.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.censohyrule.domain.common.SpecimenMapper;
import org.example.censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.censohyrule.domain.dto.response.PageableResponse;
import org.example.censohyrule.domain.dto.response.specimen.SpecimenResponse;
import org.example.censohyrule.exceptions.ResourceNotFoundException;
import org.example.censohyrule.repository.SpecimenRepository;
import org.example.censohyrule.service.SpecimenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public PageableResponse<SpecimenResponse> getAllSpecimen(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SpecimenResponse> specimenPage = specimenMapper.toDtoList(specimenRepository.findAll(pageable));

        if (specimenPage.getTotalElements() == 0)
            throw new ResourceNotFoundException("No specimens are registered in Hyrule");
        return PageableResponse.<SpecimenResponse>builder()
                .content(specimenPage.getContent())
                .page(specimenPage.getNumber())
                .size(specimenPage.getSize())
                .totalElements(specimenPage.getTotalElements())
                .last(specimenPage.isLast())
                .build();
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
