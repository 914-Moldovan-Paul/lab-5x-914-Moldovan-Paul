export interface DoctorCreate{
    name: string,
    speciality: string,
    publishDate: Date,
    experience: number,
    shifts: number,
    hospitalId: number,
    department: string
}