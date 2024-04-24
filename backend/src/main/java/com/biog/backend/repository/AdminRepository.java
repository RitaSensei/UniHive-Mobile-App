package com.biog.backend.repository;

import com.biog.backend.model.Admin;
import com.biog.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByUser(User user);
}
