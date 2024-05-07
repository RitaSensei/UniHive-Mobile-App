package com.biog.backend.service;



import com.biog.backend.model.Club;
import com.biog.backend.model.Event;
import com.biog.backend.model.School;
import com.biog.backend.model.Student;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ClubService {
    List <Event> getAllEventsByClub(UUID id);
    Club updateClub(UUID id, Club newclub, UUID... schoolId) throws AccessDeniedException;

    void deleteClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    Club getClubByStudent(UUID id);

    Club getClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    List<Club> getAll(UUID... schoolId) throws AccessDeniedException;

    School getSchoolByClub(UUID id) throws AccessDeniedException;

    List<Event> getEventsByClub(UUID id, UUID... schoolId) throws AccessDeniedException;

    List<Student> getFollowers(UUID id, UUID... schoolId);

    Club addFollowers(UUID id, List<Student> students, UUID... schoolId) throws AccessDeniedException;

    void deleteFollowers(UUID id, List<Student> students, UUID... schoolId) throws AccessDeniedException;
}
