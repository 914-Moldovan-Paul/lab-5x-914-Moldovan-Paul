import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Hospital } from 'src/app/model/Hospital';
import { Doctor } from 'src/app/model/Doctor';
import { HospitalService } from 'src/app/services/hospital.service';
import { Location } from '@angular/common';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { UserService } from 'src/app/services/user-service';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-hospital-details',
  templateUrl: './hospital-details.component.html',
  styleUrls: ['./hospital-details.component.css']
})
export class HospitalDetailsComponent extends AbstractPageContainerComponent {

  hospital: Hospital | null = null;
  doctors: Doctor[] = [];
  hospitalId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private hospitalService: HospitalService, 
    private location: Location,
    router: Router,
    activatedRoute: ActivatedRoute,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService);
  }

  override ngOnInit(): void {
    let hospitalIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(hospitalIdString == null) {
      return;
    }
    this.hospitalId = parseInt(hospitalIdString);

    this.hospitalService.getHospitalById(this.hospitalId).subscribe(result => {
      this.hospital = result;
    });
    
    super.ngOnInit();
  }

  override pageUpdate(): void {
    this.hospitalService.getAllDoctorsForHospital(this.hospitalId, this.pageNumber, this.pageSize).subscribe(result => {
      this.doctors = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
