import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private url = `${environment.apiUrl}/clients`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Client[]> { return this.http.get<Client[]>(this.url); }
  getById(id: number): Observable<Client> { return this.http.get<Client>(`${this.url}/${id}`); }
  create(client: Client): Observable<Client> { return this.http.post<Client>(this.url, client); }
  update(id: number, client: Client): Observable<Client> { return this.http.put<Client>(`${this.url}/${id}`, client); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}
