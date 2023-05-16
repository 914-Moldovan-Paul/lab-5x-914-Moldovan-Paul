package com.example.app.dto;

import com.example.app.dto.model.DoctorDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorRatingDTO {
    @Getter
    @Setter
    private DoctorDTO doctorDTO;
    @Getter
    @Setter
    private Double rating;

}
