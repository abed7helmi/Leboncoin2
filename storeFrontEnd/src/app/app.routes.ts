import { Routes } from '@angular/router';
import { AnnoncesComponent } from './components/annonces/annonces.component';
import { AnnonceDetailComponent } from './components/annonce-detail/annonce-detail.component';
import { LoginComponent } from './components/login/login.component';
import { ProfilComponent } from './components/profil/profil.component';
import { MesAnnoncesComponent } from './components/mes-annonces/mes-annonces.component';
import { AnnonceFormComponent } from './components/annonce-form/annonce-form.component';
import { ConversationsComponent } from './components/conversations/conversations.component';
import { ConversationDetailComponent } from './components/conversation-detail/conversation-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'annonces', pathMatch: 'full' },
  { path: 'annonces', component: AnnoncesComponent },
  { path: 'annonces/nouvelle', component: AnnonceFormComponent },
  { path: 'annonces/:id', component: AnnonceDetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profil', component: ProfilComponent },
  { path: 'mes-annonces', component: MesAnnoncesComponent },
  { path: 'messages', component: ConversationsComponent },
  { path: 'messages/:annonceId', component: ConversationDetailComponent }
];
