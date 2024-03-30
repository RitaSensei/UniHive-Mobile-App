package com.biog.backend.repository;

import com.biog.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface StudentRepository extends JpaRepository<Student,UUID> {


}
