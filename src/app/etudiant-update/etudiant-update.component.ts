import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EtudiantService } from '../etudiant.service';

@Component({
  selector: 'app-etudiant-update',
  templateUrl: './etudiant-update.component.html',
  styleUrls: ['./etudiant-update.component.css']
})
export class EtudiantUpdateComponent implements OnInit {
  etudiant: any = {};

  constructor(
    private etudiantService: EtudiantService,
    private route: ActivatedRoute,
    private router: Router // Injection du service Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.etudiantService.getEtudiantById(id).subscribe((data) => {
      this.etudiant = data;
    });
  }

  updateEtudiant(): void {
    this.etudiantService.updateEtudiant(this.etudiant).subscribe(() => {
      alert('Étudiant modifié avec succès!');
      this.router.navigate(['/etudiants']); // Redirection vers la liste des étudiants
    });
  }
}
