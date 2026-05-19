package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.LivraisonDTO;
import com.example.backend.dto.LivraisonRequest;
import com.example.backend.service.LivraisonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    // GET /api/livraisons
    @GetMapping
    public ResponseEntity<List<LivraisonDTO>> getAll() {
        return ResponseEntity.ok(livraisonService.getAll());
    }

    // GET /api/livraisons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LivraisonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livraisonService.getById(id));
    }

    // GET /api/livraisons/commande/{commandeId}
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<LivraisonDTO> getByCommande(@PathVariable Long commandeId) {
        return ResponseEntity.ok(livraisonService.getByCommande(commandeId));
    }

    // GET /api/livraisons/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<LivraisonDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(livraisonService.getByStatut(statut));
    }

    // POST /api/livraisons
    @PostMapping
    public ResponseEntity<LivraisonDTO> create(@Valid @RequestBody LivraisonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livraisonService.create(request));
    }

    // PUT /api/livraisons/{id}
    @PutMapping("/{id}")
    public ResponseEntity<LivraisonDTO> update(@PathVariable Long id, @Valid @RequestBody LivraisonRequest request) {
        return ResponseEntity.ok(livraisonService.update(id, request));
    }

    // PUT /api/livraisons/{id}/statut
    @PutMapping("/{id}/statut")
    public ResponseEntity<LivraisonDTO> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(livraisonService.updateStatut(id, statut));
    }

    // DELETE /api/livraisons/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livraisonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
