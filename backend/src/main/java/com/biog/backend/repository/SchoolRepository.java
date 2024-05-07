package com.biog.backend.repository;

import com.biog.backend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface SchoolRepository extends JpaRepository<School, UUID> {

    Optional<School> findBySchoolName(String schoolName);
}
