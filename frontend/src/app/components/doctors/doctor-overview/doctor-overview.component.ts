import { Component } from '@angular/core';
import { DoctorService } from 'src/app/services/doctor.service';
import { Doctor } from 'src/app/model/Doctor';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorRatingDTO } from 'src/app/dto/DoctorRatingDTO';
import { DoctorRatingWithUserHandleDTO } from 'src/app/dto/DoctorRatingWithUserHandleDTO';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user-service';

@Component({
  selector: 'app-doctor-overview',
  templateUrl: './doctor-overview.component.html',
  styleUrls: ['./doctor-overview.component.css']
})
export class DoctorOverviewComponent extends AbstractPageContainerComponent{
  minShifts: number = -1;
  doctorRatingWithUsers: DoctorRatingWithUserHandleDTO[] = [];
  constructor(
    private doctorService: DoctorService,
    router: Router,
    activatedRoute: ActivatedRoute,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService);
  }

  override ngOnInit(): void {
    super.ngOnInit();
  }

  setShifts(minShifts: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': this.pageNumber, 'shifts': minShifts}
      }
    )
  }

  override pageUpdate() {
    this.doctorService.getDoctorRatingWithUserHandles(this.pageNumber, this.pageSize, this.minShifts).subscribe(result => {
      this.doctorRatingWithUsers = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }
}
