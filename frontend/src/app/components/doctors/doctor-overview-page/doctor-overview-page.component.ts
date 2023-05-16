import { Component, Input } from '@angular/core';
import { Doctor } from 'src/app/model/Doctor';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-doctor-overview-page',
  templateUrl: './doctor-overview-page.component.html',
  styleUrls: ['./doctor-overview-page.component.css']
})
export class DoctorOverviewPageComponent extends AbstractOverviewPageComponent<Doctor> {
  
}
