import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livraison, LivraisonRequest } from '../models/livraison.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class LivraisonService {
  private url = `${environment.apiUrl}/livraisons`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Livraison[]> { return this.http.get<Livraison[]>(this.url); }
  getById(id: number): Observable<Livraison> { return this.http.get<Livraison>(`${this.url}/${id}`); }
  getByCommande(commandeId: number): Observable<Livraison> { return this.http.get<Livraison>(`${this.url}/commande/${commandeId}`); }
  create(req: LivraisonRequest): Observable<Livraison> { return this.http.post<Livraison>(this.url, req); }
  updateStatut(id: number, statut: string): Observable<Livraison> { return this.http.put<Livraison>(`${this.url}/${id}/statut?statut=${statut}`, {}); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}
