package com.example.backend.dto;

import com.example.backend.entity.Produit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProduitDTO {

    private Long id;
    @NotBlank private String nom;
    @NotNull private BigDecimal prix;
    @Min(0) private Integer stock;

    public static ProduitDTO from(Produit p) {
        ProduitDTO dto = new ProduitDTO();
        dto.id = p.getId();
        dto.nom = p.getNom();
        dto.prix = p.getPrix();
        dto.stock = p.getStock();
        return dto;
    }

    public Produit toEntity() {
        Produit p = new Produit();
        p.setNom(this.nom);
        p.setPrix(this.prix);
        p.setStock(this.stock);
        return p;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
