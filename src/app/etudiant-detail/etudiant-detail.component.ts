// etudiant-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EtudiantService } from '../etudiant.service';

@Component({
  selector: 'app-etudiant-detail',
  templateUrl: './etudiant-detail.component.html',
  styleUrls: ['./etudiant-detail.component.css']
})
export class EtudiantDetailComponent implements OnInit {
  etudiant: any;

  constructor(
    private etudiantService: EtudiantService,
    private route: ActivatedRoute,
    private router: Router  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.etudiantService.getEtudiantById(id).subscribe((data) => {
      this.etudiant = data;
    });
  }
  goBack(): void {
    this.router.navigate(['/etudiants']);
  }
}
