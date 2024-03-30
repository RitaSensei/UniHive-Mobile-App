package com.biog.backend.controller;

import lombok.AllArgsConstructor;
import org.biog.unihivebackend.model.Admin;
import org.biog.unihivebackend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/admins")
  public List<Admin> getAll() {
    return adminService.getAll();
  }

  @GetMapping("/getadmin/{id}")
  public Admin getAdmin(@PathVariable UUID id) {
    return adminService.getAdmin(id);
  }

  @PostMapping("/addadmin")
  public Admin addAdmin(@RequestBody Admin admin) {
    return adminService.addAdmin(admin);
  }

  @DeleteMapping("/deladmin/{id}")
  public void deleteAdmin(@PathVariable UUID id) {
    adminService.deleteAdmin(id);
  }

  @PutMapping("/upadmin/{id}")
  public Admin updateAdmin(@PathVariable UUID id, @RequestBody Admin newadmin) {
    return adminService.updateAdmin(id, newadmin);
  }
}
