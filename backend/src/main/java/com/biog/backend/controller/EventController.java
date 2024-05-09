package com.biog.backend.controller;

import com.biog.backend.model.Event;
import com.biog.backend.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    List<Event> getAllClubs() throws AccessDeniedException {
        return eventService.getAll();
    }
}
