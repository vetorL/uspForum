package com.example.uspForum.professor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor findByEmail(String email) {
        return professorRepository.findByEmail(email);
    }

    public List<Professor> findAll() {
        List<Professor> professors = new ArrayList<>();
        professorRepository.findAll().forEach(professors::add);
        return professors;
    }

    public Professor findByCampusAbbreviationAndNormalizedName(String campusAbbreviation, String normalizedName) {
        return professorRepository.findByCampusAbbreviationAndNormalizedName(campusAbbreviation, normalizedName);
    }

}
