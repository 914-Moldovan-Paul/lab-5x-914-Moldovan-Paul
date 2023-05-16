import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorOverviewPageComponent } from './doctor-overview-page.component';

describe('DoctorOverviewPageComponent', () => {
  let component: DoctorOverviewPageComponent;
  let fixture: ComponentFixture<DoctorOverviewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorOverviewPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorOverviewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
