package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.PaiementDTO;
import com.example.backend.dto.PaiementRequest;
import com.example.backend.service.PaiementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    // GET /api/paiements
    @GetMapping
    public ResponseEntity<List<PaiementDTO>> getAll() {
        return ResponseEntity.ok(paiementService.getAll());
    }

    // GET /api/paiements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paiementService.getById(id));
    }

    // GET /api/paiements/commande/{commandeId}
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<PaiementDTO> getByCommande(@PathVariable Long commandeId) {
        return ResponseEntity.ok(paiementService.getByCommande(commandeId));
    }

    // GET /api/paiements/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<PaiementDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(paiementService.getByStatut(statut));
    }

    // POST /api/paiements
    @PostMapping
    public ResponseEntity<PaiementDTO> create(@Valid @RequestBody PaiementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.create(request));
    }

    // PUT /api/paiements/{id}/statut
    @PutMapping("/{id}/statut")
    public ResponseEntity<PaiementDTO> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(paiementService.updateStatut(id, statut));
    }

    // DELETE /api/paiements/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paiementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
