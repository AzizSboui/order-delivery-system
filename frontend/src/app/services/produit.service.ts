import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProduitService {
  private url = `${environment.apiUrl}/produits`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Produit[]> { return this.http.get<Produit[]>(this.url); }
  getDisponibles(): Observable<Produit[]> { return this.http.get<Produit[]>(`${this.url}/disponibles`); }
  create(p: Produit): Observable<Produit> { return this.http.post<Produit>(this.url, p); }
  update(id: number, p: Produit): Observable<Produit> { return this.http.put<Produit>(`${this.url}/${id}`, p); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}
