package com.example.labs_service.repository;

import com.example.labs_service.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepository extends JpaRepository<Laboratory,Long> {
}
