import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnnonceCardComponent } from '../annonce-card/annonce-card.component';
import { AnnonceService } from '../../services/annonce.service';
import { Annonce } from '../../models/annonce.model';
import { Categorie } from '../../models/categorie.model';

@Component({
  selector: 'app-annonces',
  standalone: true,
  imports: [CommonModule, FormsModule, AnnonceCardComponent],
  templateUrl: './annonces.component.html',
  styleUrl: './annonces.component.css'
})
export class AnnoncesComponent implements OnInit {

  annonces: Annonce[] = [];
  paginatedAnnonces: Annonce[] = [];
  categories: Categorie[] = [];
  currentPage = 1;
  pageSize = 4;
  totalPages = 1;
  loading = true;
  error = '';

  searchQuery = '';
  selectedCategorieId: number | null = null;

  constructor(private annonceService: AnnonceService) {}

  ngOnInit(): void {
    this.annonceService.getCategories().subscribe({
      next: (data) => this.categories = data
    });
    this.loadAnnonces();
  }

  loadAnnonces(): void {
    this.loading = true;
    this.error = '';
    const query = this.searchQuery.trim() || undefined;
    const catId = this.selectedCategorieId || undefined;

    this.annonceService.getAnnonces(query, catId).subscribe({
      next: (data) => {
        this.annonces = data;
        this.totalPages = Math.ceil(this.annonces.length / this.pageSize);
        this.updatePage();
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les annonces.';
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    this.currentPage = 1;
    this.loadAnnonces();
  }

  updatePage(): void {
    const start = (this.currentPage - 1) * this.pageSize;
    this.paginatedAnnonces = this.annonces.slice(start, start + this.pageSize);
  }

  goToPage(page: number): void {
    this.currentPage = page;
    this.updatePage();
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.goToPage(this.currentPage - 1);
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.goToPage(this.currentPage + 1);
    }
  }

  get pages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }
}
