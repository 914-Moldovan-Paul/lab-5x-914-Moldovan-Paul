import { Component } from '@angular/core';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';
import { HospitalDoctorCountDTO } from 'src/app/dto/HospitalDoctorCountDTO';

@Component({
  selector: 'app-hospital-doctor-count-page',
  templateUrl: './hospital-doctor-count-page.component.html',
  styleUrls: ['./hospital-doctor-count-page.component.css']
})
export class HospitalDoctorCountPageComponent extends AbstractOverviewPageComponent<HospitalDoctorCountDTO> {

}
