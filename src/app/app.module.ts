import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EtudiantListComponent } from './etudiant-list/etudiant-list.component';
import { HttpClientModule } from '@angular/common/http';
import { EtudiantDetailComponent } from './etudiant-detail/etudiant-detail.component';
import { EtudiantAddComponent } from './etudiant-add/etudiant-add.component';
import { EtudiantUpdateComponent } from './etudiant-update/etudiant-update.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    EtudiantListComponent,
    EtudiantDetailComponent,
    EtudiantAddComponent,
    EtudiantUpdateComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
