package com.example.backend.dto;

import com.example.backend.entity.Livraison;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LivraisonDTO {

    private Long id;
    private Long commandeId;
    private Long transporteurId;
    private String transporteurNom;
    private LocalDate dateLivraison;
    private String statut;
    private BigDecimal cout;

    public static LivraisonDTO from(Livraison l) {
        LivraisonDTO dto = new LivraisonDTO();
        dto.id = l.getId();
        dto.commandeId = l.getCommande().getId();
        dto.dateLivraison = l.getDateLivraison();
        dto.statut = l.getStatut().name();
        dto.cout = l.getCout();
        if (l.getTransporteur() != null) {
            dto.transporteurId = l.getTransporteur().getId();
            dto.transporteurNom = l.getTransporteur().getNom();
        }
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public Long getTransporteurId() { return transporteurId; }
    public void setTransporteurId(Long transporteurId) { this.transporteurId = transporteurId; }
    public String getTransporteurNom() { return transporteurNom; }
    public void setTransporteurNom(String transporteurNom) { this.transporteurNom = transporteurNom; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }
}
