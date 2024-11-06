import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {
  private apiUrl = 'http://192.168.50.4:8087/SpringMVC/etudiant';

  constructor(private http: HttpClient) { }

  getEtudiants(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/retrieve-all-etudiants`);
  }

  getEtudiantByCin(cin: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/retrieve-etudiant-cin/${cin}`);
  }

  getEtudiantById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/retrieve-etudiant/${id}`);
  }

  addEtudiant(etudiant: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add-etudiant`, etudiant);
  }

  updateEtudiant(etudiant: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/modify-etudiant`, etudiant);
  }

  deleteEtudiant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/remove-etudiant/${id}`);
  }
}
