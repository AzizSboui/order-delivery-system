package com.example.backend.dto;

import com.example.backend.entity.Paiement;
import java.time.LocalDateTime;

public class PaiementDTO {

    private Long id;
    private Long commandeId;
    private LocalDateTime date;
    private String statut;
    private String mode;

    public static PaiementDTO from(Paiement p) {
        PaiementDTO dto = new PaiementDTO();
        dto.id = p.getId();
        dto.commandeId = p.getCommande().getId();
        dto.date = p.getDate();
        dto.statut = p.getStatut().name();
        dto.mode = p.getMode() != null ? p.getMode().name() : null;
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
}
