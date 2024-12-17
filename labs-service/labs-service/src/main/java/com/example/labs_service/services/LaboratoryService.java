package com.example.labs_service.services;

import com.example.labs_service.dto.LaboratoryRequest;
import com.example.labs_service.mappers.LabMapper;
import com.example.labs_service.model.Laboratory;
import com.example.labs_service.repository.LabRepository;
import org.springframework.stereotype.Service;

@Service
public class LaboratoryService {
    private final LabRepository laboratoryRepository;
    private LabMapper labMapper;

    public LaboratoryService(LabRepository laboratoryRepository, LabMapper labMapper) {
        this.laboratoryRepository = laboratoryRepository;
        this.labMapper = labMapper;
    }

    public Laboratory getLaboratoryById(Long id) {
        return laboratoryRepository.findById(id).orElse(null);
    }

    public void saveLab(LaboratoryRequest lab, byte[] logo) {
        Laboratory laboratory = labMapper.toEntity(lab);
        if (logo != null) {
            laboratory.setLogo(logo);
        }
        laboratoryRepository.save(laboratory);
    }
}
