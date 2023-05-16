package com.example.app.repository.specification;

import com.example.app.model.Doctor;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecifications {
    public static Specification<Doctor> shiftsBiggerThan(Integer shifts) {
        return (doctor, cq, cb) -> cb.greaterThan(doctor.get("shifts"), shifts);
    }
}
