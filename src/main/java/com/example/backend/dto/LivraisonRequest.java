package com.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LivraisonRequest {

    @NotNull private Long commandeId;
    private Long transporteurId;
    private LocalDate dateLivraison;
    private BigDecimal cout;

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public Long getTransporteurId() { return transporteurId; }
    public void setTransporteurId(Long transporteurId) { this.transporteurId = transporteurId; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }
    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }
}
