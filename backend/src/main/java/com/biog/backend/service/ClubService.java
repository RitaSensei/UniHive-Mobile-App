package com.biog.backend.service;



import com.biog.backend.model.Club;
import com.biog.backend.model.Event;
import com.biog.backend.model.School;
import com.biog.backend.model.Student;

import java.util.List;
import java.util.UUID;

public interface ClubService {
    Club addClub(Club club);
    Club updateClub(UUID id ,Club newclub);
    void deleteClub(UUID id);
    Club getClub(UUID id);
    List<Club> getAll();
    School getSchoolByClub(UUID id);
    List<Event> getEventsByClub(UUID id);
    List<Student> getFollowers(UUID id);
}
