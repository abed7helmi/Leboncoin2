import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MessageService } from '../../services/message.service';
import { AuthService } from '../../services/auth.service';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-conversation-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './conversation-detail.component.html',
  styleUrl: './conversation-detail.component.css'
})
export class ConversationDetailComponent implements OnInit, AfterViewChecked {

  @ViewChild('messagesContainer') messagesContainer!: ElementRef;

  messages: Message[] = [];
  newMessage = '';
  annonceId = 0;
  annonceTitre = '';
  loading = true;
  sending = false;
  currentUserId: number;
  private shouldScroll = false;

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    private authService: AuthService
  ) {
    this.currentUserId = this.authService.currentUser?.id ?? 0;
  }

  ngOnInit(): void {
    this.annonceId = Number(this.route.snapshot.paramMap.get('annonceId'));
    this.loadMessages();
  }

  ngAfterViewChecked(): void {
    if (this.shouldScroll) {
      this.scrollToBottom();
      this.shouldScroll = false;
    }
  }

  loadMessages(): void {
    this.messageService.getMessages(this.annonceId).subscribe({
      next: (data) => {
        this.messages = data;
        if (data.length > 0) {
          this.annonceTitre = data[0].annonce.titre;
        }
        this.loading = false;
        this.shouldScroll = true;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  envoyer(): void {
    if (!this.newMessage.trim() || this.sending) return;

    this.sending = true;
    this.messageService.envoyerMessage(this.annonceId, this.newMessage.trim()).subscribe({
      next: (msg) => {
        this.messages.push(msg);
        this.newMessage = '';
        this.sending = false;
        this.shouldScroll = true;
      },
      error: () => {
        this.sending = false;
      }
    });
  }

  isMe(msg: Message): boolean {
    return msg.expediteur.id === this.currentUserId;
  }

  formatTime(date: string): string {
    return new Date(date).toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR', {
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  }

  shouldShowDate(index: number): boolean {
    if (index === 0) return true;
    const current = new Date(this.messages[index].dateEnvoi).toDateString();
    const previous = new Date(this.messages[index - 1].dateEnvoi).toDateString();
    return current !== previous;
  }

  private scrollToBottom(): void {
    if (this.messagesContainer) {
      const el = this.messagesContainer.nativeElement;
      el.scrollTop = el.scrollHeight;
    }
  }
}
