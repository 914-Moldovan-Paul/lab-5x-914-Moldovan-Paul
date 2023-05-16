import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalDoctorCountPageComponent } from '../hospital-doctor-count-page/hospital-doctor-count-page.component';

describe('HospitalDoctorCountPageComponent', () => {
  let component: HospitalDoctorCountPageComponent;
  let fixture: ComponentFixture<HospitalDoctorCountPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalDoctorCountPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalDoctorCountPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
