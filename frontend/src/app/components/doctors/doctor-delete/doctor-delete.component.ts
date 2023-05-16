import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorService } from 'src/app/services/doctor.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-doctor-delete',
  templateUrl: './doctor-delete.component.html',
  styleUrls: ['./doctor-delete.component.css']
})
export class DoctorDeleteComponent {
  doctorId: number = 0
  serverResponse: String|null = null;
  constructor(
    private route: ActivatedRoute,
    private doctorService: DoctorService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let doctorIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(doctorIdString == null) {
      return;
    }
    this.doctorId = parseInt(doctorIdString);
  }

  goBack(): void {
    this.location.back();
  }

  goToDoctors(): void {
    this.router.navigate(["/doctors"]);
  }

  deleteDoctor(): void {
    this.doctorService.deleteDoctor(this.doctorId).subscribe({
      next: response => {
        this.serverResponse="Operation was succesful!";
      },
      error: error => {
        this.serverResponse="An error occured!";
      }
    });
  }
}
