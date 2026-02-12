import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Annonce } from '../../models/annonce.model';

@Component({
  selector: 'app-annonce-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './annonce-card.component.html',
  styleUrl: './annonce-card.component.css'
})
export class AnnonceCardComponent {

  @Input({ required: true }) annonce!: Annonce;

  get imageUrl(): string {
    if (this.annonce.images && this.annonce.images.length > 0) {
      return this.annonce.images[0].url;
    }
    return '';
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
