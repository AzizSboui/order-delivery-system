package com.example.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class CommandeRequest {

    @NotNull private Long clientId;
    @NotEmpty private List<LigneRequest> lignes;

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public List<LigneRequest> getLignes() { return lignes; }
    public void setLignes(List<LigneRequest> lignes) { this.lignes = lignes; }

    public static class LigneRequest {
        @NotNull private Long produitId;
        private int quantite;
        private BigDecimal prixUnitaire;

        public Long getProduitId() { return produitId; }
        public void setProduitId(Long produitId) { this.produitId = produitId; }
        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }
        public BigDecimal getPrixUnitaire() { return prixUnitaire; }
        public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    }
}
