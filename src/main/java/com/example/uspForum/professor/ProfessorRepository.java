package com.example.uspForum.professor;

import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {

    Professor findByEmail(String email);

    Professor findByCampusAbbreviationAndNormalizedName(String campusAbbreviation, String normalizedName);

}
