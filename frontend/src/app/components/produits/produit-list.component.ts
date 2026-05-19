import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/produit.model';

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <div class="header">
        <h2>Produits</h2>
        <button class="btn" (click)="showForm = !showForm">+ Nouveau produit</button>
      </div>

      <form *ngIf="showForm" (ngSubmit)="save()" class="inline-form">
        <input [(ngModel)]="form.nom" name="nom" placeholder="Nom" required />
        <input [(ngModel)]="form.prix" name="prix" type="number" step="0.01" placeholder="Prix (€)" required />
        <input [(ngModel)]="form.stock" name="stock" type="number" min="0" placeholder="Stock" required />
        <button type="submit" class="btn">{{ editing ? 'Modifier' : 'Créer' }}</button>
        <button type="button" (click)="cancel()">Annuler</button>
      </form>

      <table>
        <thead><tr><th>#</th><th>Nom</th><th>Prix</th><th>Stock</th><th>Actions</th></tr></thead>
        <tbody>
          <tr *ngFor="let p of produits">
            <td>{{ p.id }}</td>
            <td>{{ p.nom }}</td>
            <td>{{ p.prix | number:'1.2-2' }} €</td>
            <td [class.low-stock]="p.stock < 5">{{ p.stock }}</td>
            <td>
              <button (click)="edit(p)">Modifier</button>
              <button (click)="delete(p.id!)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];
  showForm = false;
  editing = false;
  editId?: number;
  form: Produit = { nom: '', prix: 0, stock: 0 };

  constructor(private produitService: ProduitService) {}

  ngOnInit() { this.produitService.getAll().subscribe(d => this.produits = d); }

  edit(p: Produit) {
    this.form = { ...p };
    this.editId = p.id;
    this.editing = true;
    this.showForm = true;
  }

  save() {
    if (this.editing) {
      this.produitService.update(this.editId!, this.form).subscribe(p => {
        this.produits = this.produits.map(x => x.id === p.id ? p : x);
        this.cancel();
      });
    } else {
      this.produitService.create(this.form).subscribe(p => { this.produits.push(p); this.cancel(); });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer ce produit ?')) {
      this.produitService.delete(id).subscribe(() => this.produits = this.produits.filter(p => p.id !== id));
    }
  }

  cancel() { this.showForm = false; this.editing = false; this.form = { nom: '', prix: 0, stock: 0 }; }
}
