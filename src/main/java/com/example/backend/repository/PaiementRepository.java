package com.example.backend.repository;

import com.example.backend.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByCommandeId(Long commandeId);
    List<Paiement> findByStatut(Paiement.StatutPaiement statut);
    List<Paiement> findByMode(Paiement.ModePaiement mode);
}
