package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.LivraisonDTO;
import com.example.backend.dto.LivraisonRequest;
import com.example.backend.entity.Commande;
import com.example.backend.entity.Livraison;
import com.example.backend.entity.Transporteur;
import com.example.backend.repository.CommandeRepository;
import com.example.backend.repository.LivraisonRepository;
import com.example.backend.repository.TransporteurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivraisonService {

    @Autowired
    private LivraisonRepository livraisonRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private TransporteurRepository transporteurRepository;

    public List<LivraisonDTO> getAll() {
        return livraisonRepository.findAll().stream().map(LivraisonDTO::from).collect(Collectors.toList());
    }

    public LivraisonDTO getById(Long id) {
        return LivraisonDTO.from(findOrThrow(id));
    }

    public LivraisonDTO getByCommande(Long commandeId) {
        return livraisonRepository.findByCommandeId(commandeId)
                .map(LivraisonDTO::from)
                .orElseThrow(() -> new RuntimeException("Livraison introuvable pour la commande : " + commandeId));
    }

    public List<LivraisonDTO> getByStatut(String statut) {
        return livraisonRepository.findByStatut(Livraison.StatutLivraison.valueOf(statut)).stream()
                .map(LivraisonDTO::from).collect(Collectors.toList());
    }

    @Transactional
    public LivraisonDTO create(LivraisonRequest request) {
        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + request.getCommandeId()));

        Livraison livraison = new Livraison();
        livraison.setCommande(commande);
        livraison.setDateLivraison(request.getDateLivraison());
        livraison.setCout(request.getCout());
        livraison.setStatut(Livraison.StatutLivraison.EN_PREPARATION);

        if (request.getTransporteurId() != null) {
            Transporteur transporteur = transporteurRepository.findById(request.getTransporteurId())
                    .orElseThrow(() -> new RuntimeException("Transporteur introuvable : " + request.getTransporteurId()));
            livraison.setTransporteur(transporteur);
        }

        // Ne pas rétrograder le statut si la commande est déjà plus avancée
        if (commande.getStatut() == Commande.StatutCommande.EN_ATTENTE ||
            commande.getStatut() == Commande.StatutCommande.CONFIRMEE ||
            commande.getStatut() == Commande.StatutCommande.EN_COURS) {
            commande.setStatut(Commande.StatutCommande.EXPEDIEE);
            commandeRepository.save(commande);
        }

        return LivraisonDTO.from(livraisonRepository.save(livraison));
    }

    @Transactional
    public LivraisonDTO updateStatut(Long id, String statut) {
        Livraison livraison = findOrThrow(id);
        Livraison.StatutLivraison newStatut = Livraison.StatutLivraison.valueOf(statut);
        livraison.setStatut(newStatut);

        if (newStatut == Livraison.StatutLivraison.LIVREE) {
            livraison.getCommande().setStatut(Commande.StatutCommande.LIVREE);
            commandeRepository.save(livraison.getCommande());
        }

        return LivraisonDTO.from(livraisonRepository.save(livraison));
    }

    @Transactional
    public LivraisonDTO update(Long id, LivraisonRequest request) {
        Livraison livraison = findOrThrow(id);
        livraison.setDateLivraison(request.getDateLivraison());
        livraison.setCout(request.getCout());

        if (request.getTransporteurId() != null) {
            Transporteur transporteur = transporteurRepository.findById(request.getTransporteurId())
                    .orElseThrow(() -> new RuntimeException("Transporteur introuvable : " + request.getTransporteurId()));
            livraison.setTransporteur(transporteur);
        }

        return LivraisonDTO.from(livraisonRepository.save(livraison));
    }

    public void delete(Long id) {
        findOrThrow(id);
        livraisonRepository.deleteById(id);
    }

    private Livraison findOrThrow(Long id) {
        return livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison introuvable : " + id));
    }
}
