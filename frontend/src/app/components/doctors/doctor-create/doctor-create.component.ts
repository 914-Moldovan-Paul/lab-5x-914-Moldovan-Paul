import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { DoctorService } from 'src/app/services/doctor.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { DoctorCreate } from 'src/app/model/DoctorCreate';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-doctor-create',
  templateUrl: './doctor-create.component.html',
  styleUrls: ['./doctor-create.component.css']
})
export class DoctorCreateComponent {
  createForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      speciality: ['', Validators.required],
      publishDate: [new Date(), Validators.required],
      experience: [0.0, [Validators.required, Validators.min(0)]],
      shifts: [0, [Validators.required, Validators.min(0)]],
      hospitalId: [0, Validators.required],
      department: ['', Validators.required]
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private doctorService: DoctorService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let doctorIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(doctorIdString == null) {
      return;
    }
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.doctorService.createDoctor(this.createForm.value as DoctorCreate).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse= error.error.error;
        }
      });
    }
  }

  goToDoctors(): void {
    this.router.navigate(["/doctors"]);
  }

  goBack(): void {
    this.location.back();
  }
}
