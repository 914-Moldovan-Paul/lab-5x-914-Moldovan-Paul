package com.example.app.dto.model;

import com.example.app.model.Hospital;
import com.example.app.model.Doctor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorDTO {
    @Getter
    @Setter
    private Integer id;
    @NotBlank
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String speciality;
    @Getter
    @Setter
    private Date publishDate;
    @Min(0)
    @Getter
    @Setter
    private Double experience;
    @Min(0)
    @Getter
    @Setter
    private Integer shifts;
    @Getter
    @Setter
    private Integer hospitalId;
    @Getter
    @Setter
    private String department;

    public static DoctorDTO fromDoctor(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpeciality(),
                doctor.getPublishDate(),
                doctor.getExperience(),
                doctor.getShifts(),
                doctor.getHospital().getId(),
                doctor.getDepartment()
        );
    }

    public static Doctor toDoctor(DoctorDTO doctorDTO, Hospital hospital) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setSpeciality(doctorDTO.getSpeciality());
        doctor.setPublishDate(doctorDTO.getPublishDate());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setShifts(doctorDTO.getShifts());
        doctor.setHospital(hospital);
        doctor.setDepartment(doctorDTO.getDepartment());
        return doctor;
    }



}

