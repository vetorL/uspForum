package com.example.uspForum.campus;

import org.springframework.data.repository.CrudRepository;

public interface CampusRepository extends CrudRepository<Campus, Long> {

    Campus findByAbbreviation(String abbreviation);

}
