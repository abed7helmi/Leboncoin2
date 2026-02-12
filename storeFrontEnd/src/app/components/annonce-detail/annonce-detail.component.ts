import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AnnonceService } from '../../services/annonce.service';
import { AuthService } from '../../services/auth.service';
import { MessageService } from '../../services/message.service';
import { Annonce } from '../../models/annonce.model';

@Component({
  selector: 'app-annonce-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './annonce-detail.component.html',
  styleUrl: './annonce-detail.component.css'
})
export class AnnonceDetailComponent implements OnInit {

  annonce: Annonce | null = null;
  loading = true;
  error = '';
  messageContenu = '';
  messageSending = false;
  messageSuccess = false;
  messageError = '';

  constructor(
    private route: ActivatedRoute,
    private annonceService: AnnonceService,
    public authService: AuthService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.annonceService.getAnnonceById(id).subscribe({
      next: (data) => {
        this.annonce = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Annonce introuvable.';
        this.loading = false;
      }
    });
  }

  get canContact(): boolean {
    return this.authService.isLoggedIn
      && this.annonce !== null
      && this.authService.currentUser?.id !== this.annonce.vendeur.id;
  }

  envoyerMessage(): void {
    if (!this.messageContenu.trim() || !this.annonce || this.messageSending) return;

    this.messageSending = true;
    this.messageError = '';
    this.messageSuccess = false;

    this.messageService.envoyerMessage(this.annonce.id, this.messageContenu.trim()).subscribe({
      next: () => {
        this.messageSuccess = true;
        this.messageContenu = '';
        this.messageSending = false;
      },
      error: () => {
        this.messageError = 'Erreur lors de l\'envoi du message.';
        this.messageSending = false;
      }
    });
  }

  formatPrice(prix: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(prix);
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR', {
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  }
}
