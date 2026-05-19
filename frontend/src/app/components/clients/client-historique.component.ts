import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { CommandeService } from '../../services/commande.service';
import { Client } from '../../models/client.model';
import { Commande } from '../../models/order.model';

@Component({
  selector: 'app-client-historique',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="container">
      <h2>Historique des commandes par client</h2>

      <div class="form-group" style="flex-direction:row; align-items:center; gap:12px; display:flex; margin-bottom:20px">
        <select [(ngModel)]="selectedClientId" (change)="loadHistorique()">
          <option value="">-- Sélectionner un client --</option>
          <option *ngFor="let c of clients" [value]="c.id">{{ c.nom }} ({{ c.email }})</option>
        </select>
      </div>

      <div *ngIf="selectedClientId && commandes.length === 0" style="color:#718096; padding:16px">
        Aucune commande pour ce client.
      </div>

      <table *ngIf="commandes.length > 0">
        <thead>
          <tr><th>#</th><th>Date</th><th>Statut</th><th>Total</th><th>Articles</th><th>Actions</th></tr>
        </thead>
        <tbody>
          <tr *ngFor="let c of commandes">
            <td>{{ c.id }}</td>
            <td>{{ c.date | date:'dd/MM/yyyy HH:mm' }}</td>
            <td><span class="badge badge-{{ c.statut }}">{{ c.statut }}</span></td>
            <td>{{ c.montantTotal | number:'1.2-2' }} €</td>
            <td>{{ c.lignes.length }} article(s)</td>
            <td><a [routerLink]="['/commandes', c.id]">Détails</a></td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class ClientHistoriqueComponent implements OnInit {
  clients: Client[] = [];
  commandes: Commande[] = [];
  selectedClientId: number | '' = '';

  constructor(
    private clientService: ClientService,
    private commandeService: CommandeService
  ) {}

  ngOnInit() {
    this.clientService.getAll().subscribe(d => this.clients = d);
  }

  loadHistorique() {
    if (this.selectedClientId) {
      this.commandeService.getByClient(Number(this.selectedClientId))
        .subscribe(d => this.commandes = d);
    } else {
      this.commandes = [];
    }
  }
}
