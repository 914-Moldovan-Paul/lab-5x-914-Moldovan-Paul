import { Component } from '@angular/core';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';
import { DoctorRatingWithUserHandleDTO } from 'src/app/dto/DoctorRatingWithUserHandleDTO';

@Component({
  selector: 'app-doctor-rating-with-users-page',
  templateUrl: './doctor-rating-with-users-page.component.html',
  styleUrls: ['./doctor-rating-with-users-page.component.css']
})
export class DoctorRatingWithUsersPageComponent extends AbstractOverviewPageComponent<DoctorRatingWithUserHandleDTO> {

}
