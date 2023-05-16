import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hospital } from '../model/Hospital';
import { Observable } from 'rxjs';
import { GenericPage } from '../model/GenericPage';
import { environment } from 'src/environments/environment';
import { Doctor } from '../model/Doctor';
import { HospitalDoctorCountDTO } from '../dto/HospitalDoctorCountDTO';
import { HospitalCreate } from '../model/HospitalCreate';

@Injectable({
  providedIn: 'root'
})

export class HospitalService {

  constructor(private http: HttpClient) { }

  getAllHospitals(pageNumber: number, pageSize: number): Observable<GenericPage<Hospital>> {
    return this.http.get<GenericPage<Hospital>>(environment.apiURL + "/hospitals" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getHospitalsReviewCountPage(pageNumber: number, pageSize: number): Observable<GenericPage<HospitalDoctorCountDTO>> {
    return this.http.get<GenericPage<HospitalDoctorCountDTO>>(environment.apiURL + "/hospital-doctor-counts" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllDoctorsForHospital(hospitalId: number, pageNumber: number, pageSize: number): Observable<GenericPage<Doctor>> {
    return this.http.get<GenericPage<Doctor>>(environment.apiURL + "/hospitals" + `/${hospitalId}` + "/doctors" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getHospitalById(id: number): Observable<Hospital> {
    return this.http.get<Hospital>(environment.apiURL + "/hospitals/" + id.toString());
  }

  editHospital(hospital: Hospital): Observable<any>{
    return this.http.patch(environment.apiURL + "/hospitals/" + hospital.id.toString(), hospital);
  }

  createHospital(hospital: HospitalCreate): Observable<any>{
    return this.http.post(environment.apiURL + "/hospitals", hospital);
  }

  deleteHospital(id: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/hospitals/" + id.toString());
  }

  getHospitalCountStatistic(pageNumber: number, pageSize: number): Observable<GenericPage<HospitalDoctorCountDTO>> {
    return this.http.get<GenericPage<HospitalDoctorCountDTO>>(environment.apiURL + "/hospitals/sorted-by-doctors" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }
}
