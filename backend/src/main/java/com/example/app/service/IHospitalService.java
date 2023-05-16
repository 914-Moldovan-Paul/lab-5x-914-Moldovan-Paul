package com.example.app.service;

import com.example.app.dto.HospitalDoctorCountDTO;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.exceptions.AppException;
import org.springframework.data.domain.Page;


public interface IHospitalService {
    Page<HospitalDTO> getAllHospitals(Integer pageNumber, Integer pageSize);
    HospitalDTO getHospitalById(Integer id) throws AppException;
    void createHospital(HospitalDTO hospital) throws AppException;
    void updateHospitalWithId(Integer id, HospitalDTO user ) throws AppException;
    void deleteHospitalWithId(Integer id);
    public Page<HospitalDoctorCountDTO> getHospitalsSortedByDoctors(Integer pageNumber, Integer pageSize);
    public Page<HospitalDoctorCountDTO> getHospitalDoctorCountsPage(Integer pageNumber, Integer pageSize);
}
