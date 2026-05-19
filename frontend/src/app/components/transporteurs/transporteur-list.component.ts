import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransporteurService } from '../../services/transporteur.service';
import { Transporteur } from '../../models/transporteur.model';

@Component({
  selector: 'app-transporteur-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <div class="header">
        <h2>Transporteurs</h2>
        <button class="btn" (click)="showForm = !showForm">+ Nouveau transporteur</button>
      </div>

      <form *ngIf="showForm" (ngSubmit)="save()" class="inline-form">
        <input [(ngModel)]="form.nom" name="nom" placeholder="Nom" required />
        <input [(ngModel)]="form.telephone" name="telephone" placeholder="Téléphone" required />
        <button type="submit" class="btn">{{ editing ? 'Modifier' : 'Créer' }}</button>
        <button type="button" (click)="cancel()">Annuler</button>
      </form>

      <table>
        <thead><tr><th>#</th><th>Nom</th><th>Téléphone</th><th>Actions</th></tr></thead>
        <tbody>
          <tr *ngFor="let t of transporteurs">
            <td>{{ t.id }}</td>
            <td>{{ t.nom }}</td>
            <td>{{ t.telephone }}</td>
            <td>
              <button (click)="edit(t)">Modifier</button>
              <button (click)="delete(t.id!)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class TransporteurListComponent implements OnInit {
  transporteurs: Transporteur[] = [];
  showForm = false;
  editing = false;
  editId?: number;
  form: Transporteur = { nom: '', telephone: '' };

  constructor(private transporteurService: TransporteurService) {}

  ngOnInit() { this.transporteurService.getAll().subscribe(d => this.transporteurs = d); }

  edit(t: Transporteur) {
    this.form = { ...t };
    this.editId = t.id;
    this.editing = true;
    this.showForm = true;
  }

  save() {
    if (this.editing) {
      this.transporteurService.update(this.editId!, this.form).subscribe(t => {
        this.transporteurs = this.transporteurs.map(x => x.id === t.id ? t : x);
        this.cancel();
      });
    } else {
      this.transporteurService.create(this.form).subscribe(t => { this.transporteurs.push(t); this.cancel(); });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer ce transporteur ?')) {
      this.transporteurService.delete(id).subscribe(() => this.transporteurs = this.transporteurs.filter(t => t.id !== id));
    }
  }

  cancel() { this.showForm = false; this.editing = false; this.form = { nom: '', telephone: '' }; }
}
