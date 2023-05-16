import { Component } from '@angular/core';
import { HospitalDoctorCountDTO } from 'src/app/dto/HospitalDoctorCountDTO';
import { HospitalService } from 'src/app/services/hospital.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { UserService } from 'src/app/services/user-service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-hospital-count-statistic',
  templateUrl: './hospital-count-statistic.component.html',
  styleUrls: ['./hospital-count-statistic.component.css']
})
export class HospitalCountStatisticComponent extends AbstractPageContainerComponent{
  hospitalCounts: HospitalDoctorCountDTO[] = [];
  constructor(
    private hospitalService: HospitalService,
    activatedRoute: ActivatedRoute,
    router: Router,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService)
  }

  override ngOnInit(): void {
    super.ngOnInit();
  }

  override pageUpdate(): void {
    this.hospitalService.getHospitalCountStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
      this.hospitalCounts = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }
}
