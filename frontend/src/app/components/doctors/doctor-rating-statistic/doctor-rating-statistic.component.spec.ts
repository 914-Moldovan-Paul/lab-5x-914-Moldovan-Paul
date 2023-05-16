import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorRatingStatisticComponent } from '../doctor-score-statistic/doctor-rating-statistic.component';

describe('DoctorRatingStatisticComponent', () => {
  let component: DoctorRatingStatisticComponent;
  let fixture: ComponentFixture<DoctorRatingStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorRatingStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorRatingStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
