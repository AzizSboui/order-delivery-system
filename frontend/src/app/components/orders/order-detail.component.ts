import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommandeService } from '../../services/commande.service';
import { LivraisonService } from '../../services/livraison.service';
import { PaiementService } from '../../services/paiement.service';
import { TransporteurService } from '../../services/transporteur.service';
import { Commande } from '../../models/order.model';
import { Livraison } from '../../models/livraison.model';
import { Paiement, MODES_PAIEMENT } from '../../models/paiement.model';
import { Transporteur } from '../../models/transporteur.model';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="container" *ngIf="commande">
      <a routerLink="/commandes">← Retour</a>
      <h2>Commande #{{ commande.id }}</h2>
      <p>Client : <strong>{{ commande.clientNom }}</strong></p>
      <p>Statut : <span class="badge badge-{{ commande.statut }}">{{ commande.statut }}</span></p>
      <p>Date : {{ commande.date | date:'dd/MM/yyyy HH:mm' }}</p>

      <div class="form-group">
        <label>Changer le statut</label>
        <select [(ngModel)]="newStatut">
          <option *ngFor="let s of statuts" [value]="s">{{ s }}</option>
        </select>
        <button (click)="updateStatut()">Mettre à jour</button>
      </div>

      <h3>Articles</h3>
      <table>
        <thead><tr><th>Produit</th><th>Qté</th><th>Prix unitaire</th><th>Sous-total</th></tr></thead>
        <tbody>
          <tr *ngFor="let l of commande.lignes">
            <td>{{ l.produitNom }}</td>
            <td>{{ l.quantite }}</td>
            <td>{{ l.prixUnitaire | number:'1.2-2' }} €</td>
            <td>{{ l.sousTotal | number:'1.2-2' }} €</td>
          </tr>
        </tbody>
      </table>
      <p><strong>Total : {{ commande.montantTotal | number:'1.2-2' }} €</strong></p>

      <!-- Paiement -->
      <h3>Paiement</h3>
      <div *ngIf="paiement; else noPaiement">
        <p>Mode : {{ paiement.mode }} | Statut : <strong>{{ paiement.statut }}</strong></p>
        <select [(ngModel)]="newStatutPaiement">
          <option *ngFor="let s of statutsPaiement" [value]="s">{{ s }}</option>
        </select>
        <button (click)="updateStatutPaiement()">Mettre à jour</button>
      </div>
      <ng-template #noPaiement>
        <select [(ngModel)]="modePaiement">
          <option *ngFor="let m of modesPaiement" [value]="m">{{ m }}</option>
        </select>
        <button (click)="createPaiement()">Créer un paiement</button>
      </ng-template>

      <!-- Livraison -->
      <h3>Livraison</h3>
      <div *ngIf="livraison; else noLivraison">
        <p>Transporteur : {{ livraison.transporteurNom || '-' }} | Statut : <strong>{{ livraison.statut }}</strong></p>
        <p>Date prévue : {{ livraison.dateLivraison | date:'dd/MM/yyyy' }}</p>
        <select [(ngModel)]="newStatutLivraison">
          <option *ngFor="let s of statutsLivraison" [value]="s">{{ s }}</option>
        </select>
        <button (click)="updateStatutLivraison()">Mettre à jour</button>
      </div>
      <ng-template #noLivraison>
        <div class="form-group">
          <select [(ngModel)]="transporteurId">
            <option value="">-- Transporteur (optionnel) --</option>
            <option *ngFor="let t of transporteurs" [value]="t.id">{{ t.nom }}</option>
          </select>
          <input type="date" [(ngModel)]="dateLivraison" />
          <button (click)="createLivraison()">Créer une livraison</button>
        </div>
      </ng-template>
    </div>
  `
})
export class OrderDetailComponent implements OnInit {
  commande?: Commande;
  livraison?: Livraison;
  paiement?: Paiement;
  transporteurs: Transporteur[] = [];

  newStatut = '';
  newStatutLivraison = '';
  newStatutPaiement = '';
  modePaiement = 'CARTE_BANCAIRE';
  transporteurId: number | '' = '';
  dateLivraison = '';

  statuts = ['EN_ATTENTE', 'CONFIRMEE', 'EN_COURS', 'EXPEDIEE', 'LIVREE', 'ANNULEE'];
  statutsLivraison = ['EN_PREPARATION', 'EXPEDIEE', 'EN_TRANSIT', 'LIVREE', 'ECHEC'];
  statutsPaiement = ['EN_ATTENTE', 'VALIDE', 'REFUSE', 'REMBOURSE'];
  modesPaiement = MODES_PAIEMENT;

  constructor(
    private route: ActivatedRoute,
    private commandeService: CommandeService,
    private livraisonService: LivraisonService,
    private paiementService: PaiementService,
    private transporteurService: TransporteurService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.commandeService.getById(id).subscribe(c => {
      this.commande = c;
      this.newStatut = c.statut;
    });
    this.livraisonService.getByCommande(id).subscribe({ next: l => { this.livraison = l; this.newStatutLivraison = l.statut; }, error: () => {} });
    this.paiementService.getByCommande(id).subscribe({ next: p => { this.paiement = p; this.newStatutPaiement = p.statut; }, error: () => {} });
    this.transporteurService.getAll().subscribe(t => this.transporteurs = t);
  }

  updateStatut() {
    this.commandeService.updateStatut(this.commande!.id!, this.newStatut).subscribe(c => this.commande = c);
  }

  createPaiement() {
    this.paiementService.create({ commandeId: this.commande!.id!, mode: this.modePaiement })
      .subscribe(p => { this.paiement = p; this.newStatutPaiement = p.statut; });
  }

  updateStatutPaiement() {
    this.paiementService.updateStatut(this.paiement!.id!, this.newStatutPaiement)
      .subscribe(p => this.paiement = p);
  }

  createLivraison() {
    const req: any = {
      commandeId: this.commande!.id!
    };
    if (this.transporteurId !== '' && this.transporteurId !== null) {
      req.transporteurId = Number(this.transporteurId);
    }
    if (this.dateLivraison) {
      req.dateLivraison = this.dateLivraison;
    }
    this.livraisonService.create(req).subscribe({
      next: l => { this.livraison = l; this.newStatutLivraison = l.statut; },
      error: err => alert('Erreur création livraison : ' + (err.error?.message || err.message || JSON.stringify(err.error)))
    });
  }

  updateStatutLivraison() {
    this.livraisonService.updateStatut(this.livraison!.id!, this.newStatutLivraison)
      .subscribe(l => this.livraison = l);
  }
}
