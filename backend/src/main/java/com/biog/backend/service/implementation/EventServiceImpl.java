package com.biog.backend.service.implementation;

import com.biog.backend.exception.NotFoundException;
import com.biog.backend.model.*;
import com.biog.backend.repository.ClubRepository;
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

    private final SchoolRepository schoolRepository;
    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Event> getAllByStudent() {
        return eventRepository.findAll();

    }

    @Override
    public List<Event> getAll(UUID... schoolId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return eventRepository.findAll();
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
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
            Event newevent = new Event();
            newevent.setEventName(event.getEventName());
            newevent.setEventDescription(event.getEventDescription());
            newevent.setEventCategory(event.getEventCategory());
            newevent.setEventLocation(event.getEventLocation());
            newevent.setStartTime(event.getStartTime());
            newevent.setEndTime(event.getEndTime());
            newevent.setEventBanner(event.getEventBanner());
            newevent.setClub(event.getClub());
            return eventRepository.save(newevent);
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to add events in this school");
        }
        Event newevent = new Event();
        newevent.setEventName(event.getEventName());
        newevent.setEventDescription(event.getEventDescription());
        newevent.setEventCategory(event.getEventCategory());
        newevent.setStartTime(event.getStartTime());
        newevent.setEndTime(event.getEndTime());
        newevent.setEventBanner(event.getEventBanner());
        newevent.setClub(clubRepository.findById(event.getClub().getId()).orElseThrow(
                () -> new NotFoundException(
                        "Club not found with id " + event.getClub().getId())));
        return eventRepository.save(newevent);
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
            oldevent.setClub(clubRepository.findById(newevent.getClub().getId()).orElseThrow(
                    () -> new NotFoundException(
                            "Club not found with id " + newevent.getClub().getId())));
            return eventRepository.save(oldevent);
        }
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
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
        oldevent.setClub(clubRepository.findById(newevent.getClub().getId()).orElseThrow(
                () -> new NotFoundException(
                        "Club not found with id " + newevent.getClub().getId())));
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
            UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
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
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
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
        UUID loggedInUserSchoolId = ((Admin) (authentication).getPrincipal()).getSchool().getId();
        if (!schoolId[0].equals(loggedInUserSchoolId)) {
            throw new AccessDeniedException("You do not have permission to get clubs by events in this school");
        }
        return eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event with id " + id + " not found")).getClub();
    }

}
