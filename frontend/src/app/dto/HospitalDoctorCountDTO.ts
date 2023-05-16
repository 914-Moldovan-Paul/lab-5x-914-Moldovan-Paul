import { Hospital } from "../model/Hospital";

export interface HospitalDoctorCountDTO{
    hospitalDTO: Hospital,
    doctorCount: number
}