import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MessageService } from '../../services/message.service';
import { Conversation } from '../../models/message.model';

@Component({
  selector: 'app-conversations',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './conversations.component.html',
  styleUrl: './conversations.component.css'
})
export class ConversationsComponent implements OnInit {

  conversations: Conversation[] = [];
  loading = true;

  constructor(private messageService: MessageService) {}

  ngOnInit(): void {
    this.messageService.getConversations().subscribe({
      next: (data) => {
        this.conversations = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  formatDate(date: string): string {
    const d = new Date(date);
    const now = new Date();
    const diff = now.getTime() - d.getTime();
    const days = Math.floor(diff / (1000 * 60 * 60 * 24));

    if (days === 0) {
      return d.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
    } else if (days === 1) {
      return 'Hier';
    } else if (days < 7) {
      return d.toLocaleDateString('fr-FR', { weekday: 'long' });
    }
    return d.toLocaleDateString('fr-FR', { day: 'numeric', month: 'short' });
  }
}
