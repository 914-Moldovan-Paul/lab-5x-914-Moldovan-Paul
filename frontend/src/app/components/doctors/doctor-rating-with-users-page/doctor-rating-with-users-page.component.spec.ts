import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorRatingWithUsersPageComponent } from '../doctor-score-with-users-page/doctor-rating-with-users-page.component';

describe('DoctorRatingWithUsersPageComponent', () => {
  let component: DoctorRatingWithUsersPageComponent;
  let fixture: ComponentFixture<DoctorRatingWithUsersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorRatingWithUsersPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorRatingWithUsersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
