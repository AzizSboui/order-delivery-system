package com.example.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "livraisons")
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false, unique = true)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporteur_id")
    private Transporteur transporteur;

    private LocalDate dateLivraison;

    @Enumerated(EnumType.STRING)
    private StatutLivraison statut;

    private BigDecimal cout;

    @PrePersist
    protected void onCreate() {
        if (statut == null) statut = StatutLivraison.EN_PREPARATION;
    }

    public enum StatutLivraison {
        EN_PREPARATION, EXPEDIEE, EN_TRANSIT, LIVREE, ECHEC
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Transporteur getTransporteur() { return transporteur; }
    public void setTransporteur(Transporteur transporteur) { this.transporteur = transporteur; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }
    public StatutLivraison getStatut() { return statut; }
    public void setStatut(StatutLivraison statut) { this.statut = statut; }
    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }
}
