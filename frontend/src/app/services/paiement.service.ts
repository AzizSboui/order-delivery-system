import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paiement, PaiementRequest } from '../models/paiement.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PaiementService {
  private url = `${environment.apiUrl}/paiements`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Paiement[]> { return this.http.get<Paiement[]>(this.url); }
  getByCommande(commandeId: number): Observable<Paiement> { return this.http.get<Paiement>(`${this.url}/commande/${commandeId}`); }
  create(req: PaiementRequest): Observable<Paiement> { return this.http.post<Paiement>(this.url, req); }
  updateStatut(id: number, statut: string): Observable<Paiement> { return this.http.put<Paiement>(`${this.url}/${id}/statut?statut=${statut}`, {}); }
}
