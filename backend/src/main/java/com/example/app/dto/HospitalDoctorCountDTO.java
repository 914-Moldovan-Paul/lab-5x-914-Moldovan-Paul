package com.example.app.dto;

import com.example.app.dto.model.HospitalDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class HospitalDoctorCountDTO {
    @Getter
    @Setter
    HospitalDTO hospitalDTO;
    @Getter
    @Setter
    Integer doctorCount;
}
