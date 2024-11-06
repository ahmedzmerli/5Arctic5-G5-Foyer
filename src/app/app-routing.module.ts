import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EtudiantListComponent } from './etudiant-list/etudiant-list.component';
import { EtudiantDetailComponent } from './etudiant-detail/etudiant-detail.component';
import { EtudiantAddComponent } from './etudiant-add/etudiant-add.component';
import { EtudiantUpdateComponent } from './etudiant-update/etudiant-update.component';
// Importez ici d'autres composants si nécessaire

const routes: Routes = [
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiant/:id', component: EtudiantDetailComponent },
  { path: 'add-etudiant', component: EtudiantAddComponent },
  { path: 'update-etudiant/:id', component: EtudiantUpdateComponent },
  { path: '', redirectTo: '/etudiants', pathMatch: 'full' }, // Redirection vers 'etudiants' par défaut
  { path: '**', redirectTo: '/etudiants' } // Redirection en cas de page non trouvée
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
