import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AnnonceCardComponent } from '../annonce-card/annonce-card.component';
import { AnnonceService } from '../../services/annonce.service';
import { Annonce } from '../../models/annonce.model';

@Component({
  selector: 'app-mes-annonces',
  standalone: true,
  imports: [CommonModule, RouterLink, AnnonceCardComponent],
  templateUrl: './mes-annonces.component.html',
  styleUrl: './mes-annonces.component.css'
})
export class MesAnnoncesComponent implements OnInit {

  annonces: Annonce[] = [];
  loading = true;
  error = '';

  constructor(private annonceService: AnnonceService) {}

  ngOnInit(): void {
    this.annonceService.getMesAnnonces().subscribe({
      next: (data) => {
        this.annonces = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger vos annonces.';
        this.loading = false;
      }
    });
  }
}
