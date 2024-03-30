package com.biog.backend.service;

import com.biog.backend.model.Club;
import com.biog.backend.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService{
        Student addStudent(Student student);
        Student updateStudent(UUID id ,Student newstudent);
        void deleteStudent(UUID id);
        Student getStudent(UUID id);
        List<Student> getStudentsBySchool();
        List<Club> getClubsBySchool();
}

