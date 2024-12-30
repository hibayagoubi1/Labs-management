package com.example.labs_service.services;

import com.example.labs_service.dto.ContactCreationDTO;
import com.example.labs_service.dto.LaboratoryRequest;
import com.example.labs_service.mappers.LabMapper;
import com.example.labs_service.model.Adresse;
import com.example.labs_service.model.Contact;
import com.example.labs_service.model.Laboratory;
import com.example.labs_service.repository.AdresseRepository;
import com.example.labs_service.repository.ContactRepository;
import com.example.labs_service.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryService {
    private final LabRepository laboratoryRepository;
    private final LabMapper labMapper;
    private final ContactRepository contactRepository;
    private final AdresseRepository adresseRepository;

    public Laboratory getLaboratoryById(Long id) {
        return laboratoryRepository.findByIdWithContactsAndAddresses(id);
    }

    public Laboratory saveLab(LaboratoryRequest lab, byte[] logo) {
        Laboratory laboratory = labMapper.toEntity(lab);
        if (logo != null) {
            laboratory.setLogo(logo);
        }
        return laboratoryRepository.save(laboratory);
    }
    @Transactional
    public Contact createContactWithAddress(ContactCreationDTO dto) {
        // 1. Vérifier que le laboratoire existe
        Laboratory laboratory = laboratoryRepository.findById(dto.getLaboratoryId())
                .orElseThrow(() -> new RuntimeException("Laboratory not found"));

        // 2. Créer et sauvegarder l'adresse
        Adresse adresse = new Adresse();
        adresse.setNumVoie(dto.getAdresse().getNumVoie());
        adresse.setNomVoie(dto.getAdresse().getNomVoie());
        adresse.setCodePostal(dto.getAdresse().getCodePostal());
        adresse.setVille(dto.getAdresse().getVille());
        adresse.setCommune(dto.getAdresse().getCommune());

        adresse = adresseRepository.save(adresse);

        // 3. Créer et sauvegarder le contact
        Contact contact = new Contact();
        contact.setLaboratory(laboratory);
        contact.setAdresse(adresse);
        contact.setNumTel(dto.getNumTel());
        contact.setFax(dto.getFax());
        contact.setEmail(dto.getEmail());

        return contactRepository.save(contact);
    }

    public List<Laboratory> getLabs() {
        return laboratoryRepository.findAllWithContactsAndAddresses();
}}

