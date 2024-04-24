package com.biog.backend.service.implementation;

import com.biog.backend.exception.NotFoundException;
import com.biog.backend.model.Admin;
import com.biog.backend.model.School;
import com.biog.backend.model.User;
import com.biog.backend.repository.AdminRepository;
import com.biog.backend.repository.UserRepository;
import com.biog.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin updateAdmin(UUID id, Admin newadmin) {
        Admin oldadmin = adminRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Admin with id " + id + " not found"));
        User olduser = oldadmin.getUser();
        olduser.setEmail(newadmin.getUser().getEmail());
        olduser.setPassword(passwordEncoder.encode(newadmin.getUser().getPassword()));
        userRepository.save(olduser);
        oldadmin.setFirstName(newadmin.getFirstName());
        oldadmin.setLastName(newadmin.getLastName());
        return adminRepository.save(oldadmin);
    }

    @Override
    public void deleteAdmin(UUID id) {
        userRepository.deleteById(adminRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Admin with id " + id + " not found")).getUser().getId());
        adminRepository.deleteById(id);
    }

    @Override
    public Admin getAdmin(UUID id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Admin with id " + id + " not found"));
    }

    @Override
    public School getSchoolByAdmin(UUID id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Admin with id " + id + " not found")).getSchool();
    }
}