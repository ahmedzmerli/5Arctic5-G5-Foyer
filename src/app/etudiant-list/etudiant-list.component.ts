import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EtudiantService } from '../etudiant.service';

@Component({
  selector: 'app-etudiant-list',
  templateUrl: './etudiant-list.component.html',
  styleUrls: ['./etudiant-list.component.css']
})
export class EtudiantListComponent implements OnInit {
  etudiants: any[] = [];

  constructor(
    private etudiantService: EtudiantService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.etudiantService.getEtudiants().subscribe(
      (data) => (this.etudiants = data),
      (error) => console.error('Erreur lors de la récupération des étudiants : ', error)
    );
  }

  deleteEtudiant(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {
      this.etudiantService.deleteEtudiant(id).subscribe(() => {
        alert('Étudiant supprimé avec succès!');
        this.etudiants = this.etudiants.filter((etudiant) => etudiant.idEtudiant !== id);
      });
    }
  }

  updateEtudiant(etudiant: any): void {
    this.router.navigate(['/update-etudiant', etudiant.idEtudiant]);
  }

  viewDetails(id: number): void {
    this.router.navigate(['/etudiant', id]);
  }

  navigateToAddEtudiant(): void {
    this.router.navigate(['/add-etudiant']);
  }
}
