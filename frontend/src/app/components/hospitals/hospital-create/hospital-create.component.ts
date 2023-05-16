import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalService } from 'src/app/services/hospital.service';
import { Location } from '@angular/common';
import { HospitalCreate } from 'src/app/model/HospitalCreate';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-hospital-create',
  templateUrl: './hospital-create.component.html',
  styleUrls: ['./hospital-create.component.css']
})
export class HospitalCreateComponent {
  createForm = this.formBuilder.group(
    {
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
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.loginService.getUserHandleObservable().subscribe(value => {
      if(value != null) {
        this.createForm.controls['userHandle'].setValue(value);
      }
    });
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.hospitalService.createHospital(this.createForm.value as HospitalCreate).subscribe({
        next: response => {
          console.log("ok");
          console.log(response);
          this.serverResponse="Ok";
        },
        error: error => {
          console.log("error");
          console.log(error);
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
