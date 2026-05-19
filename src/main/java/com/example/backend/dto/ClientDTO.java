package com.example.backend.dto;

import com.example.backend.entity.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientDTO {

    private Long id;
    @NotBlank private String nom;
    @NotBlank @Email private String email;
    @NotBlank private String adresse;

    public static ClientDTO from(Client c) {
        ClientDTO dto = new ClientDTO();
        dto.id = c.getId();
        dto.nom = c.getNom();
        dto.email = c.getEmail();
        dto.adresse = c.getAdresse();
        return dto;
    }

    public Client toEntity() {
        Client c = new Client();
        c.setNom(this.nom);
        c.setEmail(this.email);
        c.setAdresse(this.adresse);
        return c;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
}
