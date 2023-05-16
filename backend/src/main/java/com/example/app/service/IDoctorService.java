package com.example.app.service;

import com.example.app.dto.DoctorRatingDTO;
import com.example.app.dto.DoctorRatingWithUserHandleDTO;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.dto.model.DoctorDTO;
import com.example.app.exceptions.AppException;
import org.springframework.data.domain.Page;

public interface IDoctorService {
    Page<DoctorDTO> getAllDoctors(Integer pageNumber, Integer pageSize);
    DoctorDTO getDoctorById(Integer id);
    void createDoctor(DoctorDTO doctorDTO) throws AppException;
    void updateDoctorWithId(Integer id, DoctorDTO doctorDTO) throws AppException;
    void deleteDoctorWithId(Integer id);
    public Page<DoctorDTO> getAllDoctorsWithShiftsBiggerThan(Integer shifts, Integer pageNumber, Integer pageSize);
    public HospitalDTO getHospitalByDoctorId(Integer id);
    Page<DoctorDTO> getDoctorsByHospitalId(Integer id, Integer pageNumber, Integer pageSize) throws AppException;
    Page<DoctorRatingDTO> getDoctorsSortedByRating(Integer pageNumber, Integer pageSize);
    Page<DoctorRatingDTO> getDoctorRatingsPage(Integer shifts, Integer pageNumber, Integer pageSize);
    Page<DoctorRatingWithUserHandleDTO> getDoctorRatingsPageWithUsers(Integer shifts, Integer pageNumber, Integer pageSize);
}