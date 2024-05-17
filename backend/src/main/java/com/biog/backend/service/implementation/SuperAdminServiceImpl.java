package com.biog.backend.service.implementation;

import com.biog.backend.auth.AuthenticationResponse;
import com.biog.backend.config.JwtService;
import com.biog.backend.model.SuperAdmin;
import com.biog.backend.repository.SuperAdminRepository;
import com.biog.backend.service.SuperAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {
    private final SuperAdminRepository superAdminRepository;
    private final JwtService jwtService;

    @Override
    public List<SuperAdmin> getAll() {
        return superAdminRepository.findAll();
    }

    @Override
    public AuthenticationResponse updateSuperAdminEmail(String email) {
        SuperAdmin superAdmin = superAdminRepository.findAll().get(0);
        superAdmin.setEmail(email);
        superAdminRepository.save(superAdmin);
        var jwtToken = jwtService.generateToken(superAdmin);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
