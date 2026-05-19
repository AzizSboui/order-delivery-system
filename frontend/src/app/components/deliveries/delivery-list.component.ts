import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LivraisonService } from '../../services/livraison.service';
import { Livraison } from '../../models/livraison.model';

@Component({
  selector: 'app-delivery-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="container">
      <h2>Livraisons</h2>
      <table>
        <thead>
          <tr><th>#</th><th>Commande</th><th>Transporteur</th><th>Date prévue</th><th>Statut</th><th>Coût</th><th>Actions</th></tr>
        </thead>
        <tbody>
          <tr *ngFor="let l of livraisons">
            <td>{{ l.id }}</td>
            <td><a [routerLink]="['/commandes', l.commandeId]">#{{ l.commandeId }}</a></td>
            <td>{{ l.transporteurNom || '-' }}</td>
            <td>{{ l.dateLivraison | date:'dd/MM/yyyy' }}</td>
            <td>
              <select [(ngModel)]="l.statut" (change)="updateStatut(l)">
                <option *ngFor="let s of statuts" [value]="s">{{ s }}</option>
              </select>
            </td>
            <td>{{ l.cout ? (l.cout | number:'1.2-2') + ' €' : '-' }}</td>
            <td><button (click)="delete(l.id!)">Supprimer</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class DeliveryListComponent implements OnInit {
  livraisons: Livraison[] = [];
  statuts = ['EN_PREPARATION', 'EXPEDIEE', 'EN_TRANSIT', 'LIVREE', 'ECHEC'];

  constructor(private livraisonService: LivraisonService) {}

  ngOnInit() { this.livraisonService.getAll().subscribe(d => this.livraisons = d); }

  updateStatut(l: Livraison) {
    this.livraisonService.updateStatut(l.id!, l.statut).subscribe();
  }

  delete(id: number) {
    if (confirm('Supprimer cette livraison ?')) {
      this.livraisonService.delete(id).subscribe(() => this.livraisons = this.livraisons.filter(l => l.id !== id));
    }
  }
}
