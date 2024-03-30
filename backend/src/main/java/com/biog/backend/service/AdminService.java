package com.biog.backend.service;

import com.biog.backend.model.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<Admin> getAll();
    Admin addAdmin(Admin admin);
    Admin updateAdmin(UUID id , Admin newadmin);
    void deleteAdmin(UUID id);
    Admin getAdmin(UUID id);

}
