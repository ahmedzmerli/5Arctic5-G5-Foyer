import { Component } from '@angular/core';
import { EtudiantService } from '../etudiant.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-etudiant-add',
  templateUrl: './etudiant-add.component.html',
  styleUrls: ['./etudiant-add.component.css']
})
export class EtudiantAddComponent {
  etudiant: any = {}; // Utiliser un objet JavaScript vide pour l'étudiant

  constructor(
    private etudiantService: EtudiantService,
    private router: Router // Injection du service Router
  ) {}

  addEtudiant(): void {
    this.etudiantService.addEtudiant(this.etudiant).subscribe(() => {
      alert('Étudiant ajouté avec succès!');
      this.router.navigate(['/etudiants']); // Redirection vers la liste des étudiants
    });
  }
}
