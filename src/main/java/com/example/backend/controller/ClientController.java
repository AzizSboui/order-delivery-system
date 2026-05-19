package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.ClientDTO;
import com.example.backend.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // GET /api/clients
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    // GET /api/clients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    // GET /api/clients/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getByEmail(email));
    }

    // POST /api/clients
    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
    }

    // PUT /api/clients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    // DELETE /api/clients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
