package com.biog.backend.controller;

import com.biog.backend.model.Student;
import com.biog.backend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

  private final StudentService studentService;

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
  @PutMapping("upstudent/{schoolId}/{id}")
  public Student updateStudent(@PathVariable UUID id, @RequestBody Student newstudent, @PathVariable UUID schoolId)
          throws AccessDeniedException {
    return studentService.updateStudent(id, newstudent, schoolId);
  }
}
