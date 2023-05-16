import { Component } from '@angular/core';
import { Review } from 'src/app/model/Review';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-review-edit',
  templateUrl: './review-edit.component.html',
  styleUrls: ['./review-edit.component.css']
})
export class ReviewEditComponent {
  editForm = this.formBuilder.group(
    {
      userHandle: [""],
      doctorId: [0],
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
    private router: Router
  ) {}

  ngOnInit(): void {
    let userHandleString: string | null = this.route.snapshot.queryParamMap.get('user_handle');
    if(userHandleString == null) {
      return ;
    }
    this.editForm.controls.userHandle.setValue(userHandleString);
    let doctorIdString: string | null = this.route.snapshot.queryParamMap.get('doctor_id');
    if(doctorIdString == null) {
      return ;
    }
    this.editForm.controls.doctorId.setValue(parseInt(doctorIdString));
    this.reviewService.getReviewById(userHandleString, parseInt(doctorIdString)).subscribe(result => {
      this.editForm.setValue(result);
    });
  }

  onSubmit(): void {
    if(this.editForm.valid) {
      this.reviewService.editReview(this.editForm.value as Review).subscribe({
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
