package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatutCommande statut;

    private BigDecimal montantTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Livraison livraison;

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Paiement paiement;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
        if (statut == null) statut = StatutCommande.EN_ATTENTE;
    }

    public enum StatutCommande {
        EN_ATTENTE, CONFIRMEE, EN_COURS, EXPEDIEE, LIVREE, ANNULEE
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public StatutCommande getStatut() { return statut; }
    public void setStatut(StatutCommande statut) { this.statut = statut; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }
    public Livraison getLivraison() { return livraison; }
    public void setLivraison(Livraison livraison) { this.livraison = livraison; }
    public Paiement getPaiement() { return paiement; }
    public void setPaiement(Paiement paiement) { this.paiement = paiement; }
}
