package com.biog.backend.service;

import com.biog.backend.model.Admin;
import com.biog.backend.model.School;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<Admin> getAll();

    Admin updateAdmin(UUID id, Admin newadmin);

    void deleteAdmin(UUID id);

    Admin getAdmin(UUID id);

    School getSchoolByAdmin(UUID id);
}
