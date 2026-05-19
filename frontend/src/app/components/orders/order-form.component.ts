import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommandeService } from '../../services/commande.service';
import { ClientService } from '../../services/client.service';
import { ProduitService } from '../../services/produit.service';
import { Client } from '../../models/client.model';
import { Produit } from '../../models/produit.model';

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h2>Nouvelle commande</h2>
      <form (ngSubmit)="submit()">
        <div class="form-group">
          <label>Client</label>
          <select [(ngModel)]="clientId" name="clientId" required>
            <option value="">-- Choisir un client --</option>
            <option *ngFor="let c of clients" [value]="c.id">{{ c.nom }} ({{ c.email }})</option>
          </select>
        </div>

        <h3>Articles</h3>
        <div *ngFor="let ligne of lignes; let i = index" class="item-row">
          <select [(ngModel)]="ligne.produitId" [name]="'produit'+i" required>
            <option value="">-- Produit --</option>
            <option *ngFor="let p of produits" [value]="p.id">{{ p.nom }} ({{ p.prix }} €)</option>
          </select>
          <input [(ngModel)]="ligne.quantite" [name]="'qte'+i" type="number" min="1" placeholder="Qté" required />
          <button type="button" (click)="removeLigne(i)">✕</button>
        </div>
        <button type="button" (click)="addLigne()">+ Ajouter un article</button>

        <div class="actions">
          <button type="submit" class="btn">Créer</button>
          <button type="button" (click)="router.navigate(['/commandes'])">Annuler</button>
        </div>
      </form>
    </div>
  `
})
export class OrderFormComponent implements OnInit {
  clients: Client[] = [];
  produits: Produit[] = [];
  clientId: number | '' = '';
  lignes: { produitId: number | ''; quantite: number }[] = [{ produitId: '', quantite: 1 }];

  constructor(
    private commandeService: CommandeService,
    private clientService: ClientService,
    private produitService: ProduitService,
    public router: Router
  ) {}

  ngOnInit() {
    this.clientService.getAll().subscribe(data => this.clients = data);
    this.produitService.getDisponibles().subscribe(data => this.produits = data);
  }

  addLigne() { this.lignes.push({ produitId: '', quantite: 1 }); }
  removeLigne(i: number) { this.lignes.splice(i, 1); }

  submit() {
    this.commandeService.create({
      clientId: Number(this.clientId),
      lignes: this.lignes.map(l => ({ produitId: Number(l.produitId), quantite: l.quantite }))
    }).subscribe(() => this.router.navigate(['/commandes']));
  }
}
