import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorRatingDTO } from 'src/app/dto/DoctorRatingDTO';
import { DoctorService } from 'src/app/services/doctor.service';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { UserService } from 'src/app/services/user-service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-doctor-rating-statistic',
  templateUrl: './doctor-rating-statistic.component.html',
  styleUrls: ['./doctor-rating-statistic.component.css']
})
export class DoctorRatingStatisticComponent extends AbstractPageContainerComponent{
  
  doctorRatings: DoctorRatingDTO[] = [];
  
  constructor(
    private doctorService: DoctorService,
    activatedRoute: ActivatedRoute,
    router: Router,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService);
  }

  override ngOnInit(): void {
    super.ngOnInit();
  }

  override pageUpdate(): void {
    this.doctorService.getDoctorRatingStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
      this.doctorRatings = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }
}
