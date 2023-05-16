package com.example.app.repository;

import com.example.app.model.Hospital;
import com.example.app.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository
        extends
        JpaRepository<Doctor, Integer>,
        JpaSpecificationExecutor<Doctor>,
        IDoctorRepository {
    Page<Doctor> findAllByHospital(Hospital hospital, PageRequest pageable);
}
