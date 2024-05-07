package com.biog.backend.repository;

import com.biog.backend.model.Event;
import com.biog.backend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.club.school = :school")
    List<Event> findBySchool(@Param("school") School school);

    @Query("SELECT e FROM Event e")
    List<Event> findAll();
}
