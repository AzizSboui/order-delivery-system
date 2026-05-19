package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false, unique = true)
    private Commande commande;

    @Column(updatable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    @Enumerated(EnumType.STRING)
    private ModePaiement mode;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
        if (statut == null) statut = StatutPaiement.EN_ATTENTE;
    }

    public enum StatutPaiement { EN_ATTENTE, VALIDE, REFUSE, REMBOURSE }
    public enum ModePaiement { CARTE_BANCAIRE, VIREMENT, PAYPAL, ESPECES, CHEQUE }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public StatutPaiement getStatut() { return statut; }
    public void setStatut(StatutPaiement statut) { this.statut = statut; }
    public ModePaiement getMode() { return mode; }
    public void setMode(ModePaiement mode) { this.mode = mode; }
}
