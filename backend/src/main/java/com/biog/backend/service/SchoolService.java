package com.biog.backend.service;

import com.biog.backend.model.Club;
import com.biog.backend.model.School;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    School addSchool(School school);
    School updateSchool(UUID id , School newschool);
    void deleteSchool(UUID id);
    School getSchool(UUID id);
    List<Club> getClubsBySchool(UUID id);
}
