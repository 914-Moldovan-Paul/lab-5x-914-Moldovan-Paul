import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Location } from '@angular/common';
import { Review } from 'src/app/model/Review';
import { ReviewService } from 'src/app/services/review.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user-service';
import { Rol } from 'src/app/model/Rol';

@Component({
  selector: 'app-review-details',
  templateUrl: './review-details.component.html',
  styleUrls: ['./review-details.component.css'],
})
export class ReviewDetailsComponent {
  review: Review | null = null;
  loggedInUser: string = 'visitor';
  rol: Rol | null = null;

  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService,
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    let userHandleString: string | null =
      this.route.snapshot.queryParamMap.get('user_handle');
    if (userHandleString == null) {
      return;
    }
    let userHandle = userHandleString;
    let doctorIdString: string | null =
      this.route.snapshot.queryParamMap.get('doctor_id');
    if (doctorIdString == null) {
      return;
    }
    let doctorId = parseInt(doctorIdString);
    this.reviewService
      .getReviewById(userHandle, doctorId)
      .subscribe((result) => {
        this.review = result;
      });
    this.loginService.getUserHandleObservable().subscribe({
      next: (value) => {
        if (value != null) {
          this.loggedInUser = value;
        }
        this.userService.getUserRol(this.loggedInUser).subscribe({
          next: (rol) => {
            this.rol = rol;
          },
        });
      },
    });
  }

  goBack(): void {
    this.location.back();
  }
}
