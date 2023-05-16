import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DoctorRatingDTO } from '../dto/DoctorRatingDTO';
import { environment } from 'src/environments/environment';
import { GenericPage } from '../model/GenericPage';
import { Doctor } from '../model/Doctor';
import { Review } from '../model/Review';
import { DoctorCreate } from '../model/DoctorCreate';
import { DoctorRatingWithUserHandleDTO } from '../dto/DoctorRatingWithUserHandleDTO';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http: HttpClient) { }

  getDoctorRatingWithUserHandles(pageNumber: number, pageSize: number, shifts: number): Observable<GenericPage<DoctorRatingWithUserHandleDTO>> {
    return this.http.get<GenericPage<DoctorRatingWithUserHandleDTO>>(environment.apiURL + "/doctor-ratings-with-users" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}` + `&shifts=${shifts}`);
  }

  getDoctorRatingStatistic(pageNumber: number, pageSize: number): Observable<GenericPage<DoctorRatingDTO>> {
    return this.http.get<GenericPage<DoctorRatingDTO>>(environment.apiURL + "/doctors/sorted-by-reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllDoctors(pageNumber: number, pageSize: number): Observable<GenericPage<Doctor>> {
    return this.http.get<GenericPage<Doctor>>(environment.apiURL + "/doctors" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllDoctorsWithShiftsFilter(pageNumber: number, pageSize: number, shifts: number): Observable<GenericPage<Doctor>> {
    return this.http.get<GenericPage<Doctor>>(environment.apiURL + "/doctors/shifts-filter" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}` + `&shifts=${shifts}`);
  }

  getDoctorRatingPageWithShiftsFilter(pageNumber: number, pageSize: number, shifts: number): Observable<GenericPage<DoctorRatingDTO>> {
    return this.http.get<GenericPage<DoctorRatingDTO>>(environment.apiURL + "/doctor-ratings" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}` + `&shifts=${shifts}`);
  }

  getAllReviewsForDoctor(doctorId: number, pageNumber: number, pageSize: number): Observable<GenericPage<Review>> {
    return this.http.get<GenericPage<Review>>(environment.apiURL + "/doctors" + `/${doctorId}` + "/reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getDoctorById(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(environment.apiURL + "/doctors/" + id.toString());
  }

  editDoctor(doctor: Doctor): Observable<any>{
    return this.http.patch(environment.apiURL + "/doctors/" + doctor.id.toString(), doctor);
  }

  createDoctor(doctor: DoctorCreate): Observable<any>{
    return this.http.post(environment.apiURL + "/doctors", doctor);
  }

  deleteDoctor(id: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/doctors/" + id.toString());
  }
}
