import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Commande, CommandeRequest } from '../models/order.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CommandeService {
  private url = `${environment.apiUrl}/commandes`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Commande[]> { return this.http.get<Commande[]>(this.url); }
  getById(id: number): Observable<Commande> { return this.http.get<Commande>(`${this.url}/${id}`); }
  getByClient(clientId: number): Observable<Commande[]> { return this.http.get<Commande[]>(`${this.url}/client/${clientId}`); }
  create(req: CommandeRequest): Observable<Commande> { return this.http.post<Commande>(this.url, req); }
  updateStatut(id: number, statut: string): Observable<Commande> { return this.http.put<Commande>(`${this.url}/${id}/statut?statut=${statut}`, {}); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}
