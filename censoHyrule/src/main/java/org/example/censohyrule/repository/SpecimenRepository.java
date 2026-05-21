package org.example.censohyrule.repository;

import org.example.censohyrule.domain.entity.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecimenRepository extends JpaRepository<Specimen, UUID> {
}
