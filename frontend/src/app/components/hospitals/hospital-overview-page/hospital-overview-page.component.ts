import { Component } from '@angular/core';
import { Hospital } from 'src/app/model/Hospital';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-hospital-overview-page',
  templateUrl: './hospital-overview-page.component.html',
  styleUrls: ['./hospital-overview-page.component.css']
})
export class HospitalOverviewPageComponent extends AbstractOverviewPageComponent<Hospital> {
  
}
