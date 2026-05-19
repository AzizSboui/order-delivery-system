import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav>
      <a routerLink="/commandes" routerLinkActive="active">Commandes</a>
      <a routerLink="/livraisons" routerLinkActive="active">Livraisons</a>
      <a routerLink="/clients" routerLinkActive="active">Clients</a>
      <a routerLink="/produits" routerLinkActive="active">Produits</a>
      <a routerLink="/transporteurs" routerLinkActive="active">Transporteurs</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {}
