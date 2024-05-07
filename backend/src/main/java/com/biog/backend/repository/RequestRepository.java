package com.biog.backend.repository;

import com.biog.backend.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {

    List<Request> findBySchoolName(String schoolName);

}
