package com.example.uspForum.service;

import com.example.uspForum.model.Campus;
import com.example.uspForum.repository.CampusRepository;
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

    public Campus findById(long id) {
        return campusRepository.findById(id).orElse(null);
    }

}
