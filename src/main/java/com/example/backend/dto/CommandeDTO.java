package com.example.backend.dto;

import com.example.backend.entity.Commande;
import com.example.backend.entity.LigneCommande;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommandeDTO {

    private Long id;
    private LocalDateTime date;
    private String statut;
    private BigDecimal montantTotal;
    private Long clientId;
    private String clientNom;
    private List<LigneDTO> lignes;

    public static CommandeDTO from(Commande c) {
        CommandeDTO dto = new CommandeDTO();
        dto.id = c.getId();
        dto.date = c.getDate();
        dto.statut = c.getStatut().name();
        dto.montantTotal = c.getMontantTotal();
        dto.clientId = c.getClient().getId();
        dto.clientNom = c.getClient().getNom();
        if (c.getLignes() != null)
            dto.lignes = c.getLignes().stream().map(LigneDTO::from).collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public List<LigneDTO> getLignes() { return lignes; }
    public void setLignes(List<LigneDTO> lignes) { this.lignes = lignes; }

    public static class LigneDTO {
        private Long id;
        private Long produitId;
        private String produitNom;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal sousTotal;

        public static LigneDTO from(LigneCommande l) {
            LigneDTO dto = new LigneDTO();
            dto.id = l.getId();
            dto.produitId = l.getProduit().getId();
            dto.produitNom = l.getProduit().getNom();
            dto.quantite = l.getQuantite();
            dto.prixUnitaire = l.getPrixUnitaire();
            dto.sousTotal = l.getSousTotal();
            return dto;
        }

        public Long getId() { return id; }
        public Long getProduitId() { return produitId; }
        public String getProduitNom() { return produitNom; }
        public Integer getQuantite() { return quantite; }
        public BigDecimal getPrixUnitaire() { return prixUnitaire; }
        public BigDecimal getSousTotal() { return sousTotal; }
    }
}
