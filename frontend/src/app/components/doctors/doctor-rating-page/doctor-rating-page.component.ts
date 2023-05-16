import { Component } from '@angular/core';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';
import { DoctorRatingDTO } from 'src/app/dto/DoctorRatingDTO';

@Component({
  selector: 'app-doctor-rating-page',
  templateUrl: './doctor-rating-page.component.html',
  styleUrls: ['./doctor-rating-page.component.css']
})
export class DoctorRatingPageComponent extends AbstractOverviewPageComponent<DoctorRatingDTO> {

}
