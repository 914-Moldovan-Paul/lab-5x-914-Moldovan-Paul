package com.example.app.repository;

import com.example.app.dto.HospitalDoctorCountDTO;
import com.example.app.dto.model.HospitalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHospitalRepository {
    public Page<HospitalDoctorCountDTO> getHospitalsSortedByDoctorCount(Pageable pageable);
    public List<HospitalDoctorCountDTO> getHospitalDoctorCountsFromList(List<HospitalDTO> hospitalDTOS);

    public Integer getHospitalCountForUserHandle(String userHandle);
}
