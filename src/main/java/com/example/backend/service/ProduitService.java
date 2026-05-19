package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.ProduitDTO;
import com.example.backend.entity.Produit;
import com.example.backend.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<ProduitDTO> getAll() {
        return produitRepository.findAll().stream().map(ProduitDTO::from).collect(Collectors.toList());
    }

    public ProduitDTO getById(Long id) {
        return ProduitDTO.from(findOrThrow(id));
    }

    public List<ProduitDTO> search(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(ProduitDTO::from).collect(Collectors.toList());
    }

    public List<ProduitDTO> getDisponibles() {
        return produitRepository.findByStockGreaterThan(0).stream()
                .map(ProduitDTO::from).collect(Collectors.toList());
    }

    @Transactional
    public ProduitDTO create(ProduitDTO dto) {
        return ProduitDTO.from(produitRepository.save(dto.toEntity()));
    }

    @Transactional
    public ProduitDTO update(Long id, ProduitDTO dto) {
        Produit produit = findOrThrow(id);
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        return ProduitDTO.from(produitRepository.save(produit));
    }

    public void delete(Long id) {
        findOrThrow(id);
        produitRepository.deleteById(id);
    }

    private Produit findOrThrow(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
    }
}
