package com.biog.backend.service.implementation;

import com.biog.backend.exception.NotFoundException;
import com.biog.backend.model.*;
import com.biog.backend.repository.*;
import com.biog.backend.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List <Event> getAllEventsByClub(UUID id){
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found")).getEvents();

    }
    public Club getClubByStudent(UUID id){
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found"));
    }

    @Override
    public Club updateClub(UUID id, Club newclub, UUID... schoolId) throws AccessDeniedException {
        Club oldclub = clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            oldclub.setEmail(newclub.getEmail());
            oldclub.setClubName(newclub.getClubName());
            oldclub.setClubLogo(newclub.getClubLogo());
            oldclub.setClubDescription(newclub.getClubDescription());
            oldclub.setClubBanner(newclub.getClubBanner());
            oldclub.setClubRating(newclub.getClubRating());
            oldclub.setRatingCount(newclub.getRatingCount());
            oldclub.setSchool(schoolRepository.findById(newclub.getSchool().getId()).orElseThrow(
                    () -> new NotFoundException(
                            "School not found with id " + newclub.getSchool().getId())));
            List<Student> newStudents = newclub.getStudents().stream()
                    .map(student -> studentRepository.findById(student.getId())
                            .orElseThrow(() -> new NotFoundException(
                                    "Student not found with id " + student.getId())))
                    .collect(Collectors.toList());
            oldclub.setStudents(newStudents);
            return clubRepository.save(oldclub);
        }

        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to modify clubs in this school");
        }

        oldclub.setEmail(newclub.getEmail());
        oldclub.setPassword(passwordEncoder.encode(newclub.getPassword()));
        oldclub.setClubName(newclub.getClubName());
        oldclub.setClubLogo(newclub.getClubLogo());
        oldclub.setClubDescription(newclub.getClubDescription());
        oldclub.setClubBanner(newclub.getClubBanner());
        oldclub.setClubRating(newclub.getClubRating());
        oldclub.setRatingCount(newclub.getRatingCount());
        List<Student> newStudents = newclub.getStudents().stream()
                .map(student -> studentRepository.findById(student.getId())
                        .orElseThrow(() -> new NotFoundException(
                                "Student not found with id " + student.getId())))
                .collect(Collectors.toList());
        newStudents = newStudents.stream().filter(student -> student.getSchool().getId().equals(schoolId[0]))
                .collect(Collectors.toList());
        oldclub.setStudents(newStudents);
        return clubRepository.save(oldclub);
    }

    @Override
    public void deleteClub(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            clubRepository.deleteById(id);
        } else {
            UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
            if (!schoolId[0].equals(loggedInUserSchoolId)) {
                throw new AccessDeniedException(
                        "You do not have permission to delete clubs in this school");
            }
            clubRepository.deleteById(id);
        }
    }


    @Override
    public Club getClub(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found"));
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get clubs in this school");
        }
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found"));
    }

    @Override
    public List<Club> getAll(UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return clubRepository.findAll();
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get all clubs in this school");
        }
        School school = schoolRepository.findById(schoolId[0]).orElseThrow(
                () -> new NotFoundException("School with id " + schoolId[0] + " not found"));
        return clubRepository.findBySchool(school);
    }

    @Override
    public School getSchoolByClub(UUID id) {
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found")).getSchool();
    }

    @Override
    public List<Event> getEventsByClub(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found")).getEvents();
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException(
                    "You do not have permission to get events by clubs in this school");
        }
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found")).getEvents();
    }

    @Override
    public List<Student> getFollowers(UUID id, UUID... schoolId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found")).getStudents();
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new NotFoundException(
                    "You do not have permission to get followers of clubs in this school");
        }
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found")).getStudents();
    }

    @Override
    public Club addFollowers(UUID id, List<Student> students, UUID... schoolId)
            throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            Club club = clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found"));
            List<Student> clubStudents = club.getStudents();
            for (Student student : students) {
                if (clubStudents.contains(studentRepository
                        .findById(student.getId())
                        .orElseThrow(() -> new NotFoundException(
                                "Student with id " + student
                                        .getId()
                                        + " not found")))) {
                    throw new NotFoundException(
                            "Student with id " + student.getId() + " already follows this club");
                }
            }
            club.getStudents().addAll(
                    Arrays.asList(
                            students.stream().map(student -> studentRepository
                                            .findById(student.getId())
                                            .orElseThrow(() -> new NotFoundException(
                                                    "Student with id " + student
                                                            .getId()
                                                            + " not found")))
                                    .toArray(Student[]::new)));
            clubRepository.save(club);
            return club;
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException(
                    "You do not have permission to add followers to clubs in this school");
        }
        Club club = clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found"));
        club.getStudents().addAll(
                Arrays.asList(
                        students.stream().map(student -> studentRepository
                                        .findById(student.getId())
                                        .orElseThrow(() -> new NotFoundException(
                                                "Student with id " + student
                                                        .getId()
                                                        + " not found")))
                                .toArray(Student[]::new)));
        clubRepository.save(club);
        return club;
    }

    @Override
    public void deleteFollowers(UUID id, List<Student> students, UUID... schoolId)
            throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            Club club = clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found"));
            club.getStudents().removeAll(
                    Arrays.asList(
                            students.stream().map(student -> studentRepository
                                            .findById(student.getId())
                                            .orElseThrow(() -> new NotFoundException(
                                                    "Student with id " + student
                                                            .getId()
                                                            + " not found")))
                                    .toArray(Student[]::new)));
            clubRepository.save(club);
        } else {
            UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
            if (!schoolId[0].equals(loggedInUserSchoolId)) {
                throw new AccessDeniedException(
                        "You do not have permission to delete followers from clubs in this school");
            }
            Club club = clubRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Club with id " + id + " not found"));
            club.getStudents().removeAll(
                    Arrays.asList(
                            students.stream().map(student -> studentRepository
                                            .findById(student.getId())
                                            .orElseThrow(() -> new NotFoundException(
                                                    "Student with id " + student
                                                            .getId()
                                                            + " not found")))
                                    .toArray(Student[]::new)));
            clubRepository.save(club);
        }
    }
    @Override
    public List<Club> getClubsByStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Student with id " + id + " not found")).getClubs();
    }
}
