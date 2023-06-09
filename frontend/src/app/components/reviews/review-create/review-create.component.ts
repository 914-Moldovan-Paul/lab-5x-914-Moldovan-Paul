import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ReviewCreate } from 'src/app/model/ReviewCreate';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-review-create',
  templateUrl: './review-create.component.html',
  styleUrls: ['./review-create.component.css']
})
export class ReviewCreateComponent {
  createForm = this.formBuilder.group(
    {
      userHandle: ["", Validators.required],
      doctorId: [0, Validators.required],
      rating: [0, Validators.required],
      description: ['', Validators.required],
      postedDate: [new Date(), Validators.required],
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.queryParamMap.get('user_handle');
    if(userIdString != null) {
      this.createForm.controls.userHandle.setValue(userIdString);
    }
    let doctorIdString: string | null = this.route.snapshot.queryParamMap.get('doctor_id');
    if(doctorIdString != null) {
      this.createForm.controls.doctorId.setValue(parseInt(doctorIdString));
    }
    this.loginService.getUserHandleObservable().subscribe(value => {
      if(value != null) {
        this.createForm.controls['userHandle'].setValue(value);
      }
    });
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.reviewService.createReview(this.createForm.value as ReviewCreate).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse= error.error.error;
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}
