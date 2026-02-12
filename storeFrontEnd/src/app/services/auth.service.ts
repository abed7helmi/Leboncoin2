import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Utilisateur } from '../models/utilisateur.model';

export interface LoginResponse {
  token: string;
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'api/auth';
  private utilisateurSubject = new BehaviorSubject<LoginResponse | null>(this.getStoredUser());
  utilisateur$ = this.utilisateurSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(email: string, motDePasse: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { email, motDePasse }).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('utilisateur', JSON.stringify(response));
        this.utilisateurSubject.next(response);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('utilisateur');
    this.utilisateurSubject.next(null);
  }

  getProfil(): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.apiUrl}/profil`);
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  get isLoggedIn(): boolean {
    return this.utilisateurSubject.value !== null && this.token !== null;
  }

  get currentUser(): LoginResponse | null {
    return this.utilisateurSubject.value;
  }

  private getStoredUser(): LoginResponse | null {
    const data = localStorage.getItem('utilisateur');
    return data ? JSON.parse(data) : null;
  }
}
