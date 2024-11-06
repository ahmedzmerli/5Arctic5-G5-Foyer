import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtudiantUpdateComponent } from './etudiant-update.component';

describe('EtudiantUpdateComponent', () => {
  let component: EtudiantUpdateComponent;
  let fixture: ComponentFixture<EtudiantUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtudiantUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
