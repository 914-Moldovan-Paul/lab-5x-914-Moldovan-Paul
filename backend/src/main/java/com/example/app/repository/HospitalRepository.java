package com.example.app.repository;

import com.example.app.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository
        extends
            JpaRepository<Hospital, Integer>,
            JpaSpecificationExecutor<Hospital>,
        IHospitalRepository {

}
