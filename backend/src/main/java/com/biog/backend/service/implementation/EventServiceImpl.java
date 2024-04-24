package com.biog.backend.service.implementation;

import com.biog.backend.exception.NotFoundException;
import com.biog.backend.model.Club;
import com.biog.backend.model.Event;
import com.biog.backend.model.School;
import com.biog.backend.model.User;
import com.biog.backend.repository.AdminRepository;
import com.biog.backend.repository.EventRepository;
import com.biog.backend.repository.SchoolRepository;
import com.biog.backend.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final AdminRepository adminRepository;
    private final SchoolRepository schoolRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Event> getAll(UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return eventRepository.findAll();
        }
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get all events in this school");
        }
        School school = schoolRepository.findById(schoolId[0]).orElseThrow(
                () -> new NotFoundException("School with id " + schoolId[0] + " not found"));
        return eventRepository.findBySchool(school);
    }

    @Override
    public Event addEvent(Event event, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return eventRepository.save(event);
        }
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to add events in this school");
        }
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(UUID id, Event newevent, UUID... schoolId) throws AccessDeniedException {
        Event oldevent = eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event with id " + id + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            oldevent.setEventName(newevent.getEventName());
            oldevent.setEventDescription(newevent.getEventDescription());
            oldevent.setEventCategory(newevent.getEventCategory());
            oldevent.setStartTime(newevent.getStartTime());
            oldevent.setEndTime(newevent.getEndTime());
            oldevent.setEventBanner(newevent.getEventBanner());
            oldevent.setEventRating(newevent.getEventRating());
            oldevent.setRatingCount(newevent.getRatingCount());
            oldevent.setClub(newevent.getClub());
            return eventRepository.save(oldevent);
        }
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to update events in this school");
        }

        oldevent.setEventName(newevent.getEventName());
        oldevent.setEventDescription(newevent.getEventDescription());
        oldevent.setEventCategory(newevent.getEventCategory());
        oldevent.setStartTime(newevent.getStartTime());
        oldevent.setEndTime(newevent.getEndTime());
        oldevent.setEventBanner(newevent.getEventBanner());
        oldevent.setEventRating(newevent.getEventRating());
        oldevent.setRatingCount(newevent.getRatingCount());
        oldevent.setClub(newevent.getClub());
        return eventRepository.save(oldevent);
    }

    @Override
    public void deleteEvent(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            eventRepository.deleteById(id);
        } else {
            UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                            .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                    .getId();
            if (!schoolId[0].equals(loggedInUserSchoolId)) {
                throw new AccessDeniedException("You do not have permission to delete events in this school");
            }
            eventRepository.deleteById(id);
        }
    }

    @Override
    public Event getEvent(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return eventRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Event with id " + id + " not found"));
        }
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get events in this school");
        }
        return eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event with id " + id + " not found"));
    }

    @Override
    public Club getClubByEvent(UUID id, UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return eventRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Event with id " + id + " not found")).getClub();
        }
        UUID loggedInUserSchoolId = adminRepository.findByUser(((User) (authentication)
                        .getPrincipal())).orElseThrow(() -> new NotFoundException("Admin not found")).getSchool()
                .getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get clubs by events in this school");
        }
        return eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event with id " + id + " not found")).getClub();
    }

}
