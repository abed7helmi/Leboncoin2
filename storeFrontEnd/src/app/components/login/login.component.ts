import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email = '';
  motDePasse = '';
  error = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.error = '';
    this.loading = true;

    this.authService.login(this.email, this.motDePasse).subscribe({
      next: () => {
        this.router.navigate(['/annonces']);
      },
      error: () => {
        this.error = 'Email ou mot de passe incorrect.';
        this.loading = false;
      }
    });
  }
}
