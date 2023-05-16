import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-review-delete',
  templateUrl: './review-delete.component.html',
  styleUrls: ['./review-delete.component.css']
})
export class ReviewDeleteComponent {
  userHandle: string = "";
  doctorId: number = 0;
  serverResponse: String|null = null;
  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userHandleString: string | null = this.route.snapshot.queryParamMap.get('user_handle');
    if(userHandleString == null) {
      return;
    }
    let doctorIdString: string | null = this.route.snapshot.queryParamMap.get('doctor_id');
    if(doctorIdString == null) {
      return;
    }
    this.userHandle = userHandleString;
    this.doctorId = parseInt(doctorIdString);
  }

  goBack(): void {
    this.location.back();
  }

  deleteReview(): void {
    this.reviewService.deleteReview(this.userHandle, this.doctorId).subscribe({
      next: response => {
        this.serverResponse="Operation was succesful!";
      },
      error: error => {
        this.serverResponse="An error occured!";
      }
    });
  }
}
