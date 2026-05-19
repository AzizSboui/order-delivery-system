package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.ClientDTO;
import com.example.backend.entity.Client;
import com.example.backend.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDTO> getAll() {
        return clientRepository.findAll().stream().map(ClientDTO::from).collect(Collectors.toList());
    }

    public ClientDTO getById(Long id) {
        return ClientDTO.from(findOrThrow(id));
    }

    public ClientDTO getByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(ClientDTO::from)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'email : " + email));
    }

    @Transactional
    public ClientDTO create(ClientDTO dto) {
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé : " + dto.getEmail());
        }
        return ClientDTO.from(clientRepository.save(dto.toEntity()));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = findOrThrow(id);
        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        client.setAdresse(dto.getAdresse());
        return ClientDTO.from(clientRepository.save(client));
    }

    public void delete(Long id) {
        findOrThrow(id);
        clientRepository.deleteById(id);
    }

    private Client findOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable : " + id));
    }
}
