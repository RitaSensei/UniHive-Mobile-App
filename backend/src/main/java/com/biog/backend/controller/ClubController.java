package com.biog.backend.controller;

import com.biog.backend.model.Club;
import com.biog.backend.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/club")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/clubs")
    List<Club> getAllClubs() throws AccessDeniedException {
        return clubService.getAll();
    }
}
