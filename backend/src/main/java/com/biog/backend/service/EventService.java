package com.biog.backend.service;

import com.biog.backend.model.Club;
import com.biog.backend.model.Event;

import java.util.UUID;

public interface EventService {

    Event addEvent(Event event);
    Event updateEvent(UUID id, Event newevent);
    void deleteEvent(UUID id);
    Event getEvent(UUID id);
    Club getClubByEvent(UUID id);
}
