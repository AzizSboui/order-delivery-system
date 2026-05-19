package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.CommandeDTO;
import com.example.backend.dto.CommandeRequest;
import com.example.backend.service.CommandeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // GET /api/commandes
    @GetMapping
    public ResponseEntity<List<CommandeDTO>> getAll() {
        return ResponseEntity.ok(commandeService.getAll());
    }

    // GET /api/commandes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.getById(id));
    }

    // GET /api/commandes/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CommandeDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(commandeService.getByClient(clientId));
    }

    // GET /api/commandes/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(commandeService.getByStatut(statut));
    }

    // POST /api/commandes
    @PostMapping
    public ResponseEntity<CommandeDTO> create(@Valid @RequestBody CommandeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeService.create(request));
    }

    // PUT /api/commandes/{id}/statut
    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeDTO> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }

    // DELETE /api/commandes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
