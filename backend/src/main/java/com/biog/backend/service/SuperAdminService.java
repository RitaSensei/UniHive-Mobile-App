package com.biog.backend.service;

import com.biog.backend.auth.AuthenticationResponse;
import com.biog.backend.model.SuperAdmin;

import java.util.List;

public interface SuperAdminService {
    List<SuperAdmin> getAll();

    AuthenticationResponse updateSuperAdminEmail(String email);
}
