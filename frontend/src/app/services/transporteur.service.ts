import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transporteur } from '../models/transporteur.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class TransporteurService {
  private url = `${environment.apiUrl}/transporteurs`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Transporteur[]> { return this.http.get<Transporteur[]>(this.url); }
  create(t: Transporteur): Observable<Transporteur> { return this.http.post<Transporteur>(this.url, t); }
  update(id: number, t: Transporteur): Observable<Transporteur> { return this.http.put<Transporteur>(`${this.url}/${id}`, t); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}
