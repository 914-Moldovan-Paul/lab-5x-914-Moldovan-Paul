import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalDeleteComponent } from './hospital-delete.component';

describe('HospitalDeleteComponent', () => {
  let component: HospitalDeleteComponent;
  let fixture: ComponentFixture<HospitalDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalDeleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
