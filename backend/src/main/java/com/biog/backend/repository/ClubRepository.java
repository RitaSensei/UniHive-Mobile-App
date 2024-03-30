package com.biog.backend.repository;

import com.biog.backend.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club,UUID>{


}
