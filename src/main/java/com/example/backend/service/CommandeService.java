package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.CommandeDTO;
import com.example.backend.dto.CommandeRequest;
import com.example.backend.entity.*;
import com.example.backend.repository.ClientRepository;
import com.example.backend.repository.CommandeRepository;
import com.example.backend.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Transactional(readOnly = true)
    public List<CommandeDTO> getAll() {
        return commandeRepository.findAll().stream().map(CommandeDTO::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommandeDTO getById(Long id) {
        return CommandeDTO.from(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> getByClient(Long clientId) {
        return commandeRepository.findByClientId(clientId).stream()
                .map(CommandeDTO::from).collect(Collectors.toList());
    }

    public List<CommandeDTO> getByStatut(String statut) {
        return commandeRepository.findByStatut(Commande.StatutCommande.valueOf(statut)).stream()
                .map(CommandeDTO::from).collect(Collectors.toList());
    }

    @Transactional
    public CommandeDTO create(CommandeRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable : " + request.getClientId()));

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setStatut(Commande.StatutCommande.EN_ATTENTE);

        List<LigneCommande> lignes = request.getLignes().stream().map(lr -> {
            Produit produit = produitRepository.findById(lr.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable : " + lr.getProduitId()));

            if (produit.getStock() < lr.getQuantite()) {
                throw new RuntimeException("Stock insuffisant pour : " + produit.getNom());
            }
            produit.setStock(produit.getStock() - lr.getQuantite());
            produitRepository.save(produit);

            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(commande);
            ligne.setProduit(produit);
            ligne.setQuantite(lr.getQuantite());
            ligne.setPrixUnitaire(lr.getPrixUnitaire() != null ? lr.getPrixUnitaire() : produit.getPrix());
            return ligne;
        }).collect(Collectors.toList());

        commande.setLignes(lignes);
        commande.setMontantTotal(
            lignes.stream().map(LigneCommande::getSousTotal).reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return CommandeDTO.from(commandeRepository.save(commande));
    }

    @Transactional
    public CommandeDTO updateStatut(Long id, String statut) {
        Commande commande = findOrThrow(id);
        commande.setStatut(Commande.StatutCommande.valueOf(statut));
        return CommandeDTO.from(commandeRepository.save(commande));
    }

    public void delete(Long id) {
        findOrThrow(id);
        commandeRepository.deleteById(id);
    }

    private Commande findOrThrow(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + id));
    }
}
