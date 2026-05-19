package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotNull
    private BigDecimal prix;

    @Min(0)
    private Integer stock;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }
}
