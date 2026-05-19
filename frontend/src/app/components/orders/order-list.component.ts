import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Commande } from '../../models/order.model';
import { CommandeService } from '../../services/commande.service';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container">
      <div class="header">
        <h2>Commandes</h2>
        <a routerLink="/commandes/new" class="btn">+ Nouvelle commande</a>
      </div>
      <table>
        <thead>
          <tr><th>#</th><th>Client</th><th>Statut</th><th>Total</th><th>Date</th><th>Actions</th></tr>
        </thead>
        <tbody>
          <tr *ngFor="let c of commandes">
            <td>{{ c.id }}</td>
            <td>{{ c.clientNom }}</td>
            <td><span class="badge badge-{{ c.statut }}">{{ c.statut }}</span></td>
            <td>{{ c.montantTotal | number:'1.2-2' }} €</td>
            <td>{{ c.date | date:'dd/MM/yyyy' }}</td>
            <td>
              <a [routerLink]="['/commandes', c.id]">Détails</a>
              <button (click)="delete(c.id!)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class OrderListComponent implements OnInit {
  commandes: Commande[] = [];
  constructor(private commandeService: CommandeService) {}

  ngOnInit() {
    this.commandeService.getAll().subscribe(data => this.commandes = data);
  }

  delete(id: number) {
    if (confirm('Supprimer cette commande ?')) {
      this.commandeService.delete(id).subscribe(() => {
        this.commandes = this.commandes.filter(c => c.id !== id);
      });
    }
  }
}
