package com.example.labs_service.Controller;

import com.example.labs_service.dto.LaboratoryRequest;
import com.example.labs_service.model.Laboratory;
import com.example.labs_service.repository.LabRepository;
import com.example.labs_service.services.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labs")
public class LabController {
    @Autowired
    private LabRepository labRepository;


    @Autowired
    private LaboratoryService labService;

    @GetMapping
    public List<Laboratory> GetLabs(){
        return labRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Laboratory> getLaboratoryById(@PathVariable Long id) {
        Laboratory laboratory = labService.getLaboratoryById(id);
        if (laboratory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(laboratory);
    }
    // Endpoint pour enregistrer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute LaboratoryRequest labRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            byte[] logo = file != null ? file.getBytes() : null;
            labService.saveLab(labRequest,logo);  // Enregistrer le nouvel utilisateur
            return ResponseEntity.ok("laboratory registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
