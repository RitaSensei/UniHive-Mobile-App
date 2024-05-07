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
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final SchoolRepository schoolRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Club updateClub(UUID id, Club newclub, UUID... schoolId) throws AccessDeniedException {
        Club oldclub = clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            User olduser = oldclub.getUser();
            olduser.setEmail(newclub.getUser().getEmail());
            olduser.setPassword(passwordEncoder.encode(newclub.getUser().getPassword()));
            userRepository.save(olduser);
            oldclub.setClubName(newclub.getClubName());
            oldclub.setClubLogo(newclub.getClubLogo());
            oldclub.setClubDescription(newclub.getClubDescription());
            oldclub.setClubBanner(newclub.getClubBanner());
            oldclub.setClubRating(newclub.getClubRating());
            oldclub.setRatingCount(newclub.getRatingCount());
            oldclub.setSchool(newclub.getSchool());
            oldclub.setEvents(newclub.getEvents());
            oldclub.setStudents(newclub.getStudents());
            return clubRepository.save(oldclub);
        }

        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                .getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to modify clubs in this school");
        }

        User olduser = oldclub.getUser();
        olduser.setEmail(newclub.getUser().getEmail());
        olduser.setPassword(passwordEncoder.encode(newclub.getUser().getPassword()));
        userRepository.save(olduser);
        oldclub.setClubName(newclub.getClubName());
        oldclub.setClubLogo(newclub.getClubLogo());
        oldclub.setClubDescription(newclub.getClubDescription());
        oldclub.setClubBanner(newclub.getClubBanner());
        oldclub.setClubRating(newclub.getClubRating());
        oldclub.setRatingCount(newclub.getRatingCount());
        oldclub.setEvents(newclub.getEvents());
        oldclub.setStudents(newclub.getStudents());
        return clubRepository.save(oldclub);
    }

    @Override
    public void deleteClub(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            userRepository.deleteById(clubRepository.findById(id).orElseThrow(
                            () -> new NotFoundException("Club with id " + id + " not found")).getUser()
                    .getId());
            clubRepository.deleteById(id);
        } else {
            UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                            .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                    .getSchool()
                    .getId();
            if (!schoolId[0].equals(loggedInUserSchoolId)) {
                throw new AccessDeniedException(
                        "You do not have permission to delete clubs in this school");
            }
            userRepository.deleteById(clubRepository.findById(id).orElseThrow(
                            () -> new NotFoundException("Club with id " + id + " not found")).getUser()
                    .getId());
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
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                .getSchool()
                .getId();
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
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                .getSchool()
                .getId();
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
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                .getSchool()
                .getId();
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
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found"))
                .getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new NotFoundException(
                    "You do not have permission to get followers of clubs in this school");
        }
        return clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club with id " + id + " not found")).getStudents();
    }

}
