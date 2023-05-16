import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalService } from 'src/app/services/hospital.service';
import { Location } from '@angular/common';
import { Hospital } from 'src/app/model/Hospital';

@Component({
  selector: 'app-hospital-edit',
  templateUrl: './hospital-edit.component.html',
  styleUrls: ['./hospital-edit.component.css']
})
export class HospitalEditComponent {
  editForm = this.formBuilder.group(
    {
      id: [0],
      name: ['', Validators.required],
      address: ['', Validators.required],
      registerDate: [new Date(), Validators.required],
      userHandle: ['', Validators.required]
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private hospitalService: HospitalService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let hospitalIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(hospitalIdString == null) {
      return;
    }
    this.hospitalService.getHospitalById(parseInt(hospitalIdString)).subscribe(result => {
      this.editForm.setValue(result);
    });
  }

  onSubmit(): void {
    if(this.editForm.valid) {
      this.hospitalService.editHospital(this.editForm.value as Hospital).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse="Error";
        }
      });
    }
  }


  goToHospitals(): void {
    this.router.navigate(["/hospitals"]);
  }

  goBack(): void {
    this.location.back();
  }
}
