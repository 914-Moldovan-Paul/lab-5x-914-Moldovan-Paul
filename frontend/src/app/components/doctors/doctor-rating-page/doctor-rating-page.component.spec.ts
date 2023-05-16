import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorRatingPageComponent } from '../doctor-score-page/doctor-rating-page.component';

describe('DoctorRatingPageComponent', () => {
  let component: DoctorRatingPageComponent;
  let fixture: ComponentFixture<DoctorRatingPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorRatingPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorRatingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
