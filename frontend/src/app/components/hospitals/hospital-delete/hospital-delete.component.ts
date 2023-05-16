import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalService } from 'src/app/services/hospital.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-hospital-delete',
  templateUrl: './hospital-delete.component.html',
  styleUrls: ['./hospital-delete.component.css']
})
export class HospitalDeleteComponent {
  hospitalId: number = 0
  serverResponse: String|null = null;
  constructor(
    private route: ActivatedRoute,
    private hospitalService: HospitalService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let hospitalIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(hospitalIdString == null) {
      return;
    }
    this.hospitalId = parseInt(hospitalIdString);
  }

  goBack(): void {
    this.location.back();
  }

  goToHospitals(): void {
    this.router.navigate(["/hospitals"]);
  }

  deleteHospital(): void {
    this.hospitalService.deleteHospital(this.hospitalId).subscribe({
      next: response => {
        this.serverResponse="Operation was succesful!";
      },
      error: error => {
        this.serverResponse="An error occured!";
      }
    });
  }
}
