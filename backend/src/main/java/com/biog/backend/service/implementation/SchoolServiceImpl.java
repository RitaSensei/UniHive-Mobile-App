package com.biog.backend.service.implementation;

import com.biog.backend.exception.NotFoundException;
import com.biog.backend.model.Admin;
import com.biog.backend.model.Club;
import com.biog.backend.model.School;
import com.biog.backend.model.Student;
import com.biog.backend.repository.SchoolRepository;
import com.biog.backend.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public List<School> getAll() {
        return schoolRepository.findAll();
    }

    @Override
    public School addSchool(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public School updateSchool(UUID id, School newschool) {
        School oldschool = schoolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("School with id " + id + " not found"));

        oldschool.setSchoolName(newschool.getSchoolName());
        oldschool.setSchoolAddress(newschool.getSchoolAddress());
        oldschool.setSchoolCity(newschool.getSchoolCity());
        return schoolRepository.save(oldschool);
    }

    @Override
    public void deleteSchool(UUID id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public School getSchool(UUID id) {
        return schoolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("School with id " + id + " not found"));
    }

    @Override
    public List<Club> getClubsBySchool(UUID id) {
        return schoolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("School with id " + id + " not found")).getClubs();
    }

    @Override
    public List<Student> getStudentsBySchool(UUID id) {
        return schoolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("School with id " + id + " not found")).getStudents();
    }

    @Override
    public Admin getAdminBySchool(UUID id) {
        return schoolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("School with id " + id + " not found")).getAdmin();
    }
}
