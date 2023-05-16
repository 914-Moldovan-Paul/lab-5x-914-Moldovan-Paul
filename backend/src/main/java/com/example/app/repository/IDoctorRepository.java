package com.example.app.repository;

import com.example.app.dto.DoctorRatingDTO;
import com.example.app.dto.model.DoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDoctorRepository {
    Page<DoctorRatingDTO> getDoctorsSortedByAverageRating(Pageable pageable);
    List<DoctorRatingDTO> getDoctorRatingsFromList(List<DoctorDTO> doctorDTOS);
    public Integer getDoctorCountForUserHandle(String userHandle);
}
