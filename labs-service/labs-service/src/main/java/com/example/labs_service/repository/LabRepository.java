package com.example.labs_service.repository;

import com.example.labs_service.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabRepository extends JpaRepository<Laboratory,Long> {
    @Query("SELECT DISTINCT l FROM Laboratory l LEFT JOIN FETCH l.contacts c LEFT JOIN FETCH c.adresse")
    List<Laboratory> findAllWithContactsAndAddresses();
    @Query("SELECT l FROM Laboratory l LEFT JOIN FETCH l.contacts c LEFT JOIN FETCH c.adresse WHERE l.id = :id")
    Laboratory findByIdWithContactsAndAddresses(Long id);

}
