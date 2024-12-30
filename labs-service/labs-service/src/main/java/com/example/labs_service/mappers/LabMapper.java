package com.example.labs_service.mappers;

import com.example.labs_service.dto.LaboratoryRequest;
import com.example.labs_service.model.Laboratory;
import org.springframework.stereotype.Component;

@Component
public class LabMapper {

    public Laboratory toEntity(LaboratoryRequest request) {
        Laboratory lab = new Laboratory();
        lab.setNom(request.getNom());
        lab.setNrc(request.getNrc());
        lab.setActive(request.getActive());
        lab.setDateActivation(request.getDateActivation());

        return lab;
    }
}
