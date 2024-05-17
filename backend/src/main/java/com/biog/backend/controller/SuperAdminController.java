package com.biog.backend.controller;

import com.biog.backend.auth.AuthenticationRequest;
import com.biog.backend.auth.AuthenticationResponse;
import com.biog.backend.auth.AuthenticationService;
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
//@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminController {
    private final AdminService adminService;
    private final StudentService studentService;
    private final ClubService clubService;
    private final EventService eventService;
    private final SchoolService schoolService;
    private final RequestService requestService;
    private final SuperAdminService superAdminService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/admins")
    List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")

    @PutMapping("/upadmin/{id}")
    Admin updateAdmin(@PathVariable UUID id, @RequestBody Admin newadmin) {
        return adminService.updateAdmin(id, newadmin);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/deladmin/{id}")
    void deleteAdmin(@PathVariable UUID id) {
        adminService.deleteAdmin(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/admin/{id}")
    Admin getAdmin(@PathVariable UUID id) {
        return adminService.getAdmin(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/schools")
    List<School> getAllSchools() {
        return schoolService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/school/{id}")
    School getSchool(@PathVariable UUID id) {
        return schoolService.getSchool(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addschool")
    School addSchool(@RequestBody School school) {
        return schoolService.addSchool(school);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delschool/{id}")
    void deleteSchool(@PathVariable UUID id) {
        schoolService.deleteSchool(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upschool/{id}")
    School updateSchool(@PathVariable UUID id, @RequestBody School newschool) {
        return schoolService.updateSchool(id, newschool);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/students")
    List<Student> getAllStudents() throws AccessDeniedException {
        return studentService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upstudent/{id}")
    Student updateStudent(@PathVariable UUID id, @RequestBody Student newstudent) throws AccessDeniedException {
        return studentService.updateStudent(id, newstudent);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delstudent/{id}")
    void deleteStudent(@PathVariable UUID id) throws AccessDeniedException {
        studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/student/{id}")
    Student getStudent(@PathVariable UUID id) throws AccessDeniedException {
        return studentService.getStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/clubs")
    List<Club> getAllClubs() throws AccessDeniedException {
        return clubService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upclub/{id}")
    Club updateClub(@PathVariable UUID id, @RequestBody Club newclub) throws AccessDeniedException {
        return clubService.updateClub(id, newclub);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delclub/{id}")
    void deleteClub(@PathVariable UUID id) throws AccessDeniedException {
        clubService.deleteClub(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/club/{id}")
    Club getClub(@PathVariable UUID id) throws AccessDeniedException {
        return clubService.getClub(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addfollowers/{id}")
    Club addFollowers(@PathVariable UUID id, @RequestBody List<Student> followers) throws AccessDeniedException {
        return clubService.addFollowers(id, followers);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delfollowers/{id}")
    void deleteFollowers(@PathVariable UUID id, @RequestBody List<Student> followers) throws AccessDeniedException {
        clubService.deleteFollowers(id, followers);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/events")
    List<Event> getAllEvents() throws AccessDeniedException {
        return eventService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upevent/{id}")
    Event updateEvent(@PathVariable UUID id, @RequestBody Event newevent) throws AccessDeniedException {
        return eventService.updateEvent(id, newevent);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delevent/{id}")
    void deleteEvent(@PathVariable UUID id) throws AccessDeniedException {
        eventService.deleteEvent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/event/{id}")
    Event getEvent(@PathVariable UUID id) throws AccessDeniedException {
        return eventService.getEvent(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addevent")
    Event addEvent(@RequestBody Event event) throws AccessDeniedException {
        return eventService.addEvent(event);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/requests")
    List<Request> getAllRequests() throws AccessDeniedException {
        return requestService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/uprequest/{id}")
    Request updateRequest(@PathVariable UUID id, @RequestBody Request newrequest) throws AccessDeniedException {
        return requestService.updateRequest(id, newrequest);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delrequest/{id}")
    void deleteRequest(@PathVariable UUID id) throws AccessDeniedException {
        requestService.deleteRequest(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/request/{id}")
    Request getRequest(@PathVariable UUID id) throws AccessDeniedException {
        return requestService.getRequest(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/allcounts")
    List<Integer> getAllCounts() throws AccessDeniedException {
        return List.of(adminService.getAll().size(), studentService.getAll().size(), clubService.getAll().size(),
                eventService.getAll().size(), schoolService.getAll().size(), requestService.getAll().size());
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/all")
    List<SuperAdmin> getAll() {
        return superAdminService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/upemail")
    AuthenticationResponse updateSuperAdminEmail(@RequestParam String email) {
        return superAdminService.updateSuperAdminEmail(email);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/uppassword")
    AuthenticationResponse updateSuperAdminPassword(@RequestBody AuthenticationRequest password) {
        return authenticationService.changePassword(password);
    }
}
