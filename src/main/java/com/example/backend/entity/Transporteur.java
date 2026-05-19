package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transporteurs")
public class Transporteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String telephone;

    @OneToMany(mappedBy = "transporteur")
    private List<Livraison> livraisons = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public List<Livraison> getLivraisons() { return livraisons; }
    public void setLivraisons(List<Livraison> livraisons) { this.livraisons = livraisons; }
}
