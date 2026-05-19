package com.example.backend.dto;

import jakarta.validation.constraints.NotNull;

public class PaiementRequest {

    @NotNull private Long commandeId;
    @NotNull private String mode;

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
}
