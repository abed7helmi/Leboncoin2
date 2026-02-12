import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message, Conversation } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private apiUrl = 'api/messages';

  constructor(private http: HttpClient) {}

  envoyerMessage(annonceId: number, contenu: string): Observable<Message> {
    return this.http.post<Message>(this.apiUrl, { annonceId, contenu });
  }

  getConversations(): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${this.apiUrl}/conversations`);
  }

  getMessages(annonceId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/conversation/${annonceId}`);
  }
}
