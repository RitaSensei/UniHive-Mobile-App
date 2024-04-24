package com.biog.backend.repository;

import com.biog.backend.model.School;
import com.biog.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findBySchool(School school);
}
