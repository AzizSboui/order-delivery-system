package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.dto.TransporteurDTO;
import com.example.backend.entity.Transporteur;
import com.example.backend.repository.TransporteurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransporteurService {

    @Autowired
    private TransporteurRepository transporteurRepository;

    public List<TransporteurDTO> getAll() {
        return transporteurRepository.findAll().stream().map(TransporteurDTO::from).collect(Collectors.toList());
    }

    public TransporteurDTO getById(Long id) {
        return TransporteurDTO.from(findOrThrow(id));
    }

    @Transactional
    public TransporteurDTO create(TransporteurDTO dto) {
        return TransporteurDTO.from(transporteurRepository.save(dto.toEntity()));
    }

    @Transactional
    public TransporteurDTO update(Long id, TransporteurDTO dto) {
        Transporteur t = findOrThrow(id);
        t.setNom(dto.getNom());
        t.setTelephone(dto.getTelephone());
        return TransporteurDTO.from(transporteurRepository.save(t));
    }

    public void delete(Long id) {
        findOrThrow(id);
        transporteurRepository.deleteById(id);
    }

    private Transporteur findOrThrow(Long id) {
        return transporteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transporteur introuvable : " + id));
    }
}
