package com.example.uspForum.campus;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampusService {

    private final CampusRepository campusRepository;

    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    public List<Campus> findAll() {
        List<Campus> campi = new ArrayList<>();
        campusRepository.findAll().forEach(campi::add);
        return campi;
    }

    public Campus findByAbbreviation(String abbreviation) {
        return campusRepository.findByAbbreviation(abbreviation);
    }

}
