package com.biog.backend.repository;

import com.biog.backend.model.Club;
import com.biog.backend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club, UUID> {

    List<Club> findBySchool(School school);

    Optional<Club> findByEmail(String email);

}
