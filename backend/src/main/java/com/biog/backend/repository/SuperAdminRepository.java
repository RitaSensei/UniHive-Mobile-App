package com.biog.backend.repository;

import com.biog.backend.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface SuperAdminRepository extends JpaRepository<SuperAdmin,UUID>{


}
