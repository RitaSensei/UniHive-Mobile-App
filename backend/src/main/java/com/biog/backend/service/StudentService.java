package com.biog.backend.service;

import com.biog.backend.auth.AuthenticationResponse;
import com.biog.backend.model.Club;
import com.biog.backend.model.School;
import com.biog.backend.model.Student;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface StudentService {
        List<Student> getAll(UUID... schoolId) throws AccessDeniedException;

        Student updateStudent(UUID id, Student newstudent, UUID... schoolId) throws AccessDeniedException;

        void deleteStudent(UUID id, UUID... schoolId) throws AccessDeniedException;

        Student getStudent(UUID id, UUID... schoolId) throws AccessDeniedException;

        Student getStudentByEmail(String email);

        School getSchoolByStudent(UUID id);

        List<Club> getClubsByFollower(UUID id, UUID... schoolId) throws AccessDeniedException;

        AuthenticationResponse updateStudentEmail(UUID id, String email);

        Student updateStudentProfileImage(UUID id, String profileImage);

        void unfollowClub(UUID studentId, UUID clubId);
}

