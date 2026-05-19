package com.example.backend.dto;

import com.example.backend.entity.Transporteur;
import jakarta.validation.constraints.NotBlank;

public class TransporteurDTO {

    private Long id;
    @NotBlank private String nom;
    @NotBlank private String telephone;

    public static TransporteurDTO from(Transporteur t) {
        TransporteurDTO dto = new TransporteurDTO();
        dto.id = t.getId();
        dto.nom = t.getNom();
        dto.telephone = t.getTelephone();
        return dto;
    }

    public Transporteur toEntity() {
        Transporteur t = new Transporteur();
        t.setNom(this.nom);
        t.setTelephone(this.telephone);
        return t;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
