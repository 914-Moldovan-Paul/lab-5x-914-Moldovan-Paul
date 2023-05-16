import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalCountStatisticComponent } from './hospital-count-statistic.component';

describe('HospitalCountStatisticComponent', () => {
  let component: HospitalCountStatisticComponent;
  let fixture: ComponentFixture<HospitalCountStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalCountStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalCountStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
