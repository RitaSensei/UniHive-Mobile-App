package com.biog.backend.controller;

import com.biog.backend.service.*;
import com.biog.backend.model.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/superadmin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminController {
    private final AdminService adminService;
    private final StudentService studentService;
    private final ClubService clubService;
    private final EventService eventService;
    private final SchoolService schoolService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/admins")
    List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upadmin/{id}")
    Admin updateAdmin(UUID id, Admin newadmin) {
        return adminService.updateAdmin(id, newadmin);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/deladmin/{id}")
    void deleteAdmin(UUID id) {
        adminService.deleteAdmin(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/admin/{id}")
    Admin getAdmin(UUID id) {
        return adminService.getAdmin(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/schools")
    List<School> getAllSchools() {
        return schoolService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/school/{id}")
    School getSchool(UUID id) {
        return schoolService.getSchool(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addschool")
    School addSchool(School school) {
        return schoolService.addSchool(school);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delschool/{id}")
    void deleteSchool(UUID id) {
        schoolService.deleteSchool(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upschool/{id}")
    School updateSchool(UUID id, School newschool) {
        return schoolService.updateSchool(id, newschool);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/students")
    List<Student> getAllStudents() throws AccessDeniedException {
        return studentService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upstudent/{id}")
    Student updateStudent(UUID id, Student newstudent) throws AccessDeniedException {
        return studentService.updateStudent(id, newstudent);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delstudent/{id}")
    void deleteStudent(UUID id) throws AccessDeniedException {
        studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/student/{id}")
    Student getStudent(UUID id) throws AccessDeniedException {
        return studentService.getStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/clubs")
    List<Club> getAllClubs() throws AccessDeniedException {
        return clubService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upclub/{id}")
    Club updateClub(UUID id, Club newclub) throws AccessDeniedException {
        return clubService.updateClub(id, newclub);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delclub/{id}")
    void deleteClub(UUID id) throws AccessDeniedException {
        clubService.deleteClub(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/club/{id}")
    Club getClub(UUID id) throws AccessDeniedException {
        return clubService.getClub(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/events")
    List<Event> getAllEvents() throws AccessDeniedException {
        return eventService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upevent/{id}")
    Event updateEvent(UUID id, Event newevent) throws AccessDeniedException {
        return eventService.updateEvent(id, newevent);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delevent/{id}")
    void deleteEvent(UUID id) throws AccessDeniedException {
        eventService.deleteEvent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/event/{id}")
    Event getEvent(UUID id) throws AccessDeniedException {
        return eventService.getEvent(id);
    }
}
