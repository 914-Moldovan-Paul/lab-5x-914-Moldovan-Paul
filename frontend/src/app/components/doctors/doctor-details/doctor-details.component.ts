import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/model/Doctor';
import { DoctorService } from 'src/app/services/doctor.service';
import { Location } from '@angular/common';
import { Review } from 'src/app/model/Review';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { UserService } from 'src/app/services/user-service';
import { LoginService } from 'src/app/services/login.service';
import { HospitalService } from 'src/app/services/hospital.service';
import { Hospital } from 'src/app/model/Hospital';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css']
})
export class DoctorDetailsComponent extends AbstractPageContainerComponent {
  doctor: Doctor | null = null;
  reviews: Review[] = [];
  doctorId: number = 0;
  hospital: Hospital | null = null;

  constructor(
    private route: ActivatedRoute, 
    private doctorService: DoctorService, 
    private location: Location,
    router: Router,
    activatedRoute: ActivatedRoute,
    userPreferencesService: UserPreferencesService,
    userService: UserService,
    loginService: LoginService,
    private manufactuererService: HospitalService
  ) {
    super(router, activatedRoute, userPreferencesService, userService, loginService);
  }

  override ngOnInit(): void {
    let doctorIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(doctorIdString == null) {
      return;
    }
    this.doctorId = parseInt(doctorIdString);

    this.doctorService.getDoctorById(this.doctorId).subscribe(result => {
      this.doctor = result;
      this.manufactuererService.getHospitalById(this.doctor.hospitalId).subscribe(hospital => {
        this.hospital = hospital;
      });
    });

    super.ngOnInit();
  }

  override pageUpdate() {
    this.doctorService.getAllReviewsForDoctor(this.doctorId, this.pageNumber, this.pageSize).subscribe(result => {
      this.reviews = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
