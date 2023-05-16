import { Doctor } from "../model/Doctor";

export interface DoctorRatingDTO{
    doctorDTO: Doctor,
    rating: number
}