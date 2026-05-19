package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.TransporteurDTO;
import com.example.backend.service.TransporteurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transporteurs")
public class TransporteurController {

    @Autowired
    private TransporteurService transporteurService;

    // GET /api/transporteurs
    @GetMapping
    public ResponseEntity<List<TransporteurDTO>> getAll() {
        return ResponseEntity.ok(transporteurService.getAll());
    }

    // GET /api/transporteurs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TransporteurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transporteurService.getById(id));
    }

    // POST /api/transporteurs
    @PostMapping
    public ResponseEntity<TransporteurDTO> create(@Valid @RequestBody TransporteurDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transporteurService.create(dto));
    }

    // PUT /api/transporteurs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TransporteurDTO> update(@PathVariable Long id, @Valid @RequestBody TransporteurDTO dto) {
        return ResponseEntity.ok(transporteurService.update(id, dto));
    }

    // DELETE /api/transporteurs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transporteurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
