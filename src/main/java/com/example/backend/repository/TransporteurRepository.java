package com.example.backend.repository;

import com.example.backend.entity.Transporteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransporteurRepository extends JpaRepository<Transporteur, Long> {
    Optional<Transporteur> findByTelephone(String telephone);
}
