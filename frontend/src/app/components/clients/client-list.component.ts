import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <div class="header">
        <h2>Clients</h2>
        <button class="btn" (click)="showForm = !showForm">+ Nouveau client</button>
      </div>

      <form *ngIf="showForm" (ngSubmit)="save()" class="inline-form">
        <input [(ngModel)]="form.nom" name="nom" placeholder="Nom" required />
        <input [(ngModel)]="form.email" name="email" type="email" placeholder="Email" required />
        <input [(ngModel)]="form.adresse" name="adresse" placeholder="Adresse" required />
        <button type="submit" class="btn">{{ editing ? 'Modifier' : 'Créer' }}</button>
        <button type="button" (click)="cancel()">Annuler</button>
      </form>

      <table>
        <thead><tr><th>#</th><th>Nom</th><th>Email</th><th>Adresse</th><th>Actions</th></tr></thead>
        <tbody>
          <tr *ngFor="let c of clients">
            <td>{{ c.id }}</td>
            <td>{{ c.nom }}</td>
            <td>{{ c.email }}</td>
            <td>{{ c.adresse }}</td>
            <td>
              <button (click)="edit(c)">Modifier</button>
              <button (click)="delete(c.id!)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  showForm = false;
  editing = false;
  editId?: number;
  form: Client = { nom: '', email: '', adresse: '' };

  constructor(private clientService: ClientService) {}

  ngOnInit() { this.clientService.getAll().subscribe(d => this.clients = d); }

  edit(c: Client) {
    this.form = { ...c };
    this.editId = c.id;
    this.editing = true;
    this.showForm = true;
  }

  save() {
    if (this.editing) {
      this.clientService.update(this.editId!, this.form).subscribe(c => {
        this.clients = this.clients.map(x => x.id === c.id ? c : x);
        this.cancel();
      });
    } else {
      this.clientService.create(this.form).subscribe(c => { this.clients.push(c); this.cancel(); });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer ce client ?')) {
      this.clientService.delete(id).subscribe(() => this.clients = this.clients.filter(c => c.id !== id));
    }
  }

  cancel() { this.showForm = false; this.editing = false; this.form = { nom: '', email: '', adresse: '' }; }
}
