import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AnnonceService, AnnonceRequest } from '../../services/annonce.service';
import { Categorie } from '../../models/categorie.model';

@Component({
  selector: 'app-annonce-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './annonce-form.component.html',
  styleUrl: './annonce-form.component.css'
})
export class AnnonceFormComponent implements OnInit {

  categories: Categorie[] = [];
  form: AnnonceRequest = {
    titre: '',
    description: '',
    prix: 0,
    ville: '',
    categorieId: 0,
    imageUrl: ''
  };
  loading = false;
  error = '';

  constructor(private annonceService: AnnonceService, private router: Router) {}

  ngOnInit(): void {
    this.annonceService.getCategories().subscribe({
      next: (data) => this.categories = data,
      error: () => this.error = 'Impossible de charger les categories.'
    });
  }

  onSubmit(): void {
    this.error = '';
    this.loading = true;

    this.annonceService.createAnnonce(this.form).subscribe({
      next: () => {
        this.router.navigate(['/mes-annonces']);
      },
      error: () => {
        this.error = 'Erreur lors de la creation de l\'annonce.';
        this.loading = false;
      }
    });
  }

  get isValid(): boolean {
    return this.form.titre.trim() !== ''
      && this.form.description.trim() !== ''
      && this.form.prix > 0
      && this.form.ville.trim() !== ''
      && this.form.categorieId > 0;
  }
}
