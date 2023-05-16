import { Component } from '@angular/core';
import { Doctor } from 'src/app/model/Doctor';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { DoctorService } from 'src/app/services/doctor.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';


@Component({
  selector: 'app-doctor-edit',
  templateUrl: './doctor-edit.component.html',
  styleUrls: ['./doctor-edit.component.css']
})
export class DoctorEditComponent {
  editForm = this.formBuilder.group(
    {
      id: [0],
      name: ['', Validators.required],
      speciality: ['', Validators.required],
      publishDate: [new Date(), Validators.required],
      experience: [0, [Validators.required, Validators.min(0)]],
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
    this.doctorService.getDoctorById(parseInt(doctorIdString)).subscribe(result => {
      this.editForm.setValue(result);
    });
  }

  onSubmit(): void {
    if(this.editForm.valid) {
      this.doctorService.editDoctor(this.editForm.value as Doctor).subscribe({
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
