package com.example.backend.repository;

import com.example.backend.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
    Optional<Livraison> findByCommandeId(Long commandeId);
    List<Livraison> findByTransporteurId(Long transporteurId);
    List<Livraison> findByStatut(Livraison.StatutLivraison statut);
}
