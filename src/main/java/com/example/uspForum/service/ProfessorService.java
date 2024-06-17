package com.example.uspForum.service;

import com.example.uspForum.model.Professor;
import com.example.uspForum.repository.ProfessorRepository;
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

}
