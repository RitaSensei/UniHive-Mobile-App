package com.biog.backend.service;



import com.biog.backend.model.Club;
import com.biog.backend.model.Event;
import com.biog.backend.model.School;
import com.biog.backend.model.Student;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ClubService {
    Club updateClub(UUID id, Club newclub, UUID... schoolId) throws AccessDeniedException;

    void deleteClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    Club getClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    List<Club> getAll(UUID... schoolId) throws AccessDeniedException;

    School getSchoolByClub(UUID id) throws AccessDeniedException;

    List<Event> getEventsByClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    List<Student> getFollowers(UUID id, UUID... schoolId);
}
