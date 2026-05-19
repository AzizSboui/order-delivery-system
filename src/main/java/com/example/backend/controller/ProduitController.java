package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.ProduitDTO;
import com.example.backend.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // GET /api/produits
    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getAll() {
        return ResponseEntity.ok(produitService.getAll());
    }

    // GET /api/produits/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    // GET /api/produits/search?nom=...
    @GetMapping("/search")
    public ResponseEntity<List<ProduitDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(produitService.search(nom));
    }

    // GET /api/produits/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<ProduitDTO>> getDisponibles() {
        return ResponseEntity.ok(produitService.getDisponibles());
    }

    // POST /api/produits
    @PostMapping
    public ResponseEntity<ProduitDTO> create(@Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.create(dto));
    }

    // PUT /api/produits/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> update(@PathVariable Long id, @Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.update(id, dto));
    }

    // DELETE /api/produits/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
