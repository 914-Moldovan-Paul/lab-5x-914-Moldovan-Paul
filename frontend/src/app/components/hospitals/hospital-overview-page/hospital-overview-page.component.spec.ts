import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalOverviewPageComponent } from './hospital-overview-page.component';

describe('HospitalOverviewPageComponent', () => {
  let component: HospitalOverviewPageComponent;
  let fixture: ComponentFixture<HospitalOverviewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalOverviewPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalOverviewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
