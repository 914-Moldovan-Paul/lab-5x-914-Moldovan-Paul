package com.example.app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorRatingWithUserHandleDTO {
    @Getter
    @Setter
    private DoctorRatingDTO doctorRatingDTO;
    @Getter
    @Setter
    private String userHandle;
}
