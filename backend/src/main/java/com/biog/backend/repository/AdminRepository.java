package com.biog.backend.repository;

import com.biog.backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin,UUID> {
}
