import { Routes } from '@angular/router';
import { OrderListComponent } from './components/orders/order-list.component';
import { OrderFormComponent } from './components/orders/order-form.component';
import { OrderDetailComponent } from './components/orders/order-detail.component';
import { DeliveryListComponent } from './components/deliveries/delivery-list.component';
import { ClientListComponent } from './components/clients/client-list.component';
import { ProduitListComponent } from './components/produits/produit-list.component';
import { TransporteurListComponent } from './components/transporteurs/transporteur-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'commandes', pathMatch: 'full' },
  { path: 'commandes', component: OrderListComponent },
  { path: 'commandes/new', component: OrderFormComponent },
  { path: 'commandes/:id', component: OrderDetailComponent },
  { path: 'livraisons', component: DeliveryListComponent },
  { path: 'clients', component: ClientListComponent },
  { path: 'produits', component: ProduitListComponent },
  { path: 'transporteurs', component: TransporteurListComponent },
];
