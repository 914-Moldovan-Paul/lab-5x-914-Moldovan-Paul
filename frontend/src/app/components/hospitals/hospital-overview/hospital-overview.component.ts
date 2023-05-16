import { Component, OnInit } from '@angular/core';
import { HospitalService } from 'src/app/services/hospital.service';
import { Hospital } from 'src/app/model/Hospital';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalDoctorCountDTO } from 'src/app/dto/HospitalDoctorCountDTO';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { UserService } from 'src/app/services/user-service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-users-overview',
  templateUrl: './hospital-overview.component.html',
  styleUrls: ['./hospital-overview.component.css']
})
export class HospitalOverviewComponent extends AbstractPageContainerComponent{
  hospitalDoctorCounts: HospitalDoctorCountDTO[] = [];
  constructor(
    private hospitalService: HospitalService,
    router: Router,
    activatedRoute: ActivatedRoute,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService);
  }

  override pageUpdate(): void {
    this.hospitalService.getHospitalsReviewCountPage(this.pageNumber, this.pageSize).subscribe(result => {
      this.hospitalDoctorCounts = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }
}
