import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review } from '../model/Review';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { ReviewCreate } from '../model/ReviewCreate';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }
  
  getReviewById(userHandle: string, doctorId: number): Observable<Review> {
    return this.http.get<Review>(environment.apiURL + "/reviews", {
      params: {
        user_handle: userHandle,
        doctor_id: doctorId
      },
    });
  }

  createReview(review: ReviewCreate): Observable<any>{
    return this.http.post(environment.apiURL + "/reviews", review);
  }

  editReview(review: Review): Observable<any>{
    return this.http.patch(environment.apiURL + "/reviews", review, {
      params: {
        user_handle: review.userHandle,
        doctor_id: review.doctorId
      },
    });
  }

  deleteReview(userHandle: string, doctorId: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/reviews", {
      params: {
        user_handle: userHandle,
        doctor_id: doctorId
      },
    });
  }
}
