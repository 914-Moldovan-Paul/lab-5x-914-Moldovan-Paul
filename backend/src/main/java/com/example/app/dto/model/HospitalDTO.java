package com.example.app.dto.model;

import com.example.app.model.Hospital;
import com.example.app.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class HospitalDTO {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    @NotBlank
    private String name;
    @Getter
    @Setter
    @NotBlank
    private String address;
    @Getter
    @Setter
    private Date registerDate;
    @Getter
    @Setter
    private String userHandle;

    public static HospitalDTO fromHospital(Hospital hospital){
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getRegisterDate(),
                hospital.getUserHandle()
        );
    }

    public static Hospital toHospital(HospitalDTO hospitalDTO, User user){
        Hospital hospital = new Hospital();
        hospital.setId(hospital.getId());
        hospital.setAddress(hospitalDTO.getAddress());
        hospital.setName(hospitalDTO.getName());
        hospital.setRegisterDate(hospitalDTO.getRegisterDate());
        hospital.setUser(user);
        hospital.setUserHandle(hospitalDTO.getUserHandle());
        return hospital;
    }
}
