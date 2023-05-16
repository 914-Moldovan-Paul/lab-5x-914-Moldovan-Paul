export interface ReviewCreate{
    userHandle: string,
    doctorId: number,
    rating: number,
    description: string,
    postedDate: Date,
}