import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Annonce } from '../models/annonce.model';
import { Categorie } from '../models/categorie.model';

export interface AnnonceRequest {
  titre: string;
  description: string;
  prix: number;
  ville: string;
  categorieId: number;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {

  private apiUrl = 'api/annonces';

  constructor(private http: HttpClient) {}

  getAnnonces(query?: string, categorieId?: number): Observable<Annonce[]> {
    let params = new HttpParams();
    if (query) {
      params = params.set('q', query);
    }
    if (categorieId) {
      params = params.set('categorieId', categorieId.toString());
    }
    return this.http.get<Annonce[]>(this.apiUrl, { params });
  }

  getAnnonceById(id: number): Observable<Annonce> {
    return this.http.get<Annonce>(`${this.apiUrl}/${id}`);
  }

  getMesAnnonces(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/mes-annonces`);
  }

  createAnnonce(request: AnnonceRequest): Observable<Annonce> {
    return this.http.post<Annonce>(this.apiUrl, request);
  }

  getCategories(): Observable<Categorie[]> {
    return this.http.get<Categorie[]>('api/categories');
  }
}
