package com.biog.backend.controller;

import com.biog.backend.auth.AuthenticationRequest;
import com.biog.backend.auth.AuthenticationResponse;
import com.biog.backend.auth.AuthenticationService;
import com.biog.backend.model.Club;
import com.biog.backend.model.Event;
import com.biog.backend.model.Student;
import com.biog.backend.service.ClubService;
import com.biog.backend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.biog.backend.service.EventService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
    private final EventService eventService;
    private final ClubService clubService;
    private final StudentService studentService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{id}")
    Student getStudent(@PathVariable UUID id) throws AccessDeniedException {
        return studentService.getStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/email/{email}")
    Student getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/events")
    List<Event> getAllEvents() {
        return eventService.getAllByStudent();
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/club/{id}")
    Club getClub(@PathVariable UUID id) {
        return clubService.getClubByStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/club/{id}/events")
    List<Event> getAllEventsByClub(@PathVariable UUID id) {
        return clubService.getAllEventsByClub(id);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/upemail/{id}")
    AuthenticationResponse updateStudentByEmail(@PathVariable UUID id, @RequestParam String email) {
        return studentService.updateStudentEmail(id, email);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/upprofileimage/{id}")
    Student updateStudentProfileImage(@PathVariable UUID id, @RequestParam String profileImage) {
        return studentService.updateStudentProfileImage(id, profileImage);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/uppassword")
    AuthenticationResponse updateStudentPassword(@RequestBody AuthenticationRequest password) {
        return authenticationService.changePassword(password);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/clubs/{id}")
    List<Club> getClubsByStudent(@PathVariable UUID id) {
        return clubService.getClubsByStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @DeleteMapping("/delete/{id}")
    void deleteStudent(@PathVariable UUID id) throws AccessDeniedException {
        studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/test")
    void test() {
        System.out.println("test");
    }

}
