package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.PaiementDTO;
import com.example.backend.dto.PaiementRequest;
import com.example.backend.entity.Commande;
import com.example.backend.entity.Paiement;
import com.example.backend.repository.CommandeRepository;
import com.example.backend.repository.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private CommandeRepository commandeRepository;

    public List<PaiementDTO> getAll() {
        return paiementRepository.findAll().stream().map(PaiementDTO::from).collect(Collectors.toList());
    }

    public PaiementDTO getById(Long id) {
        return PaiementDTO.from(findOrThrow(id));
    }

    public PaiementDTO getByCommande(Long commandeId) {
        return paiementRepository.findByCommandeId(commandeId)
                .map(PaiementDTO::from)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable pour la commande : " + commandeId));
    }

    public List<PaiementDTO> getByStatut(String statut) {
        return paiementRepository.findByStatut(Paiement.StatutPaiement.valueOf(statut)).stream()
                .map(PaiementDTO::from).collect(Collectors.toList());
    }

    @Transactional
    public PaiementDTO create(PaiementRequest request) {
        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + request.getCommandeId()));

        Paiement paiement = new Paiement();
        paiement.setCommande(commande);
        paiement.setMode(Paiement.ModePaiement.valueOf(request.getMode()));
        paiement.setStatut(Paiement.StatutPaiement.EN_ATTENTE);

        return PaiementDTO.from(paiementRepository.save(paiement));
    }

    @Transactional
    public PaiementDTO updateStatut(Long id, String statut) {
        Paiement paiement = findOrThrow(id);
        Paiement.StatutPaiement newStatut = Paiement.StatutPaiement.valueOf(statut);
        paiement.setStatut(newStatut);

        if (newStatut == Paiement.StatutPaiement.VALIDE) {
            paiement.getCommande().setStatut(Commande.StatutCommande.CONFIRMEE);
            commandeRepository.save(paiement.getCommande());
        }

        return PaiementDTO.from(paiementRepository.save(paiement));
    }

    public void delete(Long id) {
        findOrThrow(id);
        paiementRepository.deleteById(id);
    }

    private Paiement findOrThrow(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable : " + id));
    }
}
