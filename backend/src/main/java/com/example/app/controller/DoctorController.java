package com.example.app.controller;

import com.example.app.dto.DoctorRatingWithUserHandleDTO;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.dto.model.DoctorDTO;
import com.example.app.dto.DoctorRatingDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IHospitalService;
import com.example.app.service.IDoctorService;
import com.example.app.service.IReviewService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@CrossOrigin
@Validated
public class DoctorController {
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IHospitalService hospitalService;

    @GetMapping(path="/doctors")
    public @ResponseBody ResponseEntity<Page<DoctorDTO>> getDoctors(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("User") User user
    ){
        if(!user.getUserRol().isRead_all()){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(doctorService.getAllDoctors( pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/doctors/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<DoctorDTO> getDoctor(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
        ) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        if(doctorDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            try {
                HospitalDTO hospitalDTO = hospitalService.getHospitalById(doctorDTO.getHospitalId());
                if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } catch (AppException e) {
                throw new RuntimeException(e);
            }

            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
        }
    }

    @GetMapping(path="/doctors/{id}/hospital", produces = "application/json")
    public @ResponseBody ResponseEntity<HospitalDTO> getDoctorHospital(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        HospitalDTO hospitalDTO = doctorService.getHospitalByDoctorId(id);
        if(hospitalDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
             return new ResponseEntity<>(hospitalDTO, HttpStatus.OK);
        }
    }

    @PostMapping(path="/doctors", produces = "application/json")
    public ResponseEntity<Map<String, String>> createDoctor(
            @Valid @RequestBody DoctorDTO doctorDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();

        if(!user.getUserRol().isCreat()) {
            response.put("message", "Unauthorized to create resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            HospitalDTO hospitalDTO = hospitalService.getHospitalById(doctorDTO.getHospitalId());
            if(!Objects.equals(hospitalDTO.getUserHandle(), user.getHandle())) {
                throw new AppException("Hospital doesn't match user");
            }
            doctorService.createDoctor(doctorDTO);
            response.put("message", "Doctor created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/doctors/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> updateDoctor(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DoctorDTO doctorDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            DoctorDTO oldDoctorDTO = doctorService.getDoctorById(id);
            if(oldDoctorDTO == null) {
                throw new AppException("No doctor with such id exists");
            }
            if(!user.getUserRol().isUpdate_all()) {
                HospitalDTO oldHospitalDTO = hospitalService.getHospitalById(oldDoctorDTO.getHospitalId());
                HospitalDTO hospitalDTO = hospitalService.getHospitalById(doctorDTO.getHospitalId());
                if(!Objects.equals(hospitalDTO.getUserHandle(), user.getHandle())) {
                    throw new AppException("Hospital doesn't match user");
                }
                if(!Objects.equals(oldHospitalDTO.getUserHandle(), hospitalDTO.getUserHandle())){
                    response.put("error", "Unauthorized to transfer resource between users");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            }

            doctorService.updateDoctorWithId(id, doctorDTO);
            response.put("message", "Doctor updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/doctors/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteDoctor(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        try {
            HospitalDTO hospitalDTO = hospitalService.getHospitalById(doctorDTO.getHospitalId());
            if(!user.getUserRol().isDelete_all() && (!user.getUserRol().isDelete_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                response.put("error", "Unauthorized to delete resource");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        doctorService.deleteDoctorWithId(id);
        response.put("message", "Doctor deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path="/doctors/shifts-filter", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<DoctorDTO>> getAllDoctorsWithShiftsBiggerThan(
            @RequestParam Integer shifts,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(doctorService.getAllDoctorsWithShiftsBiggerThan(shifts, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/doctors/sorted-by-reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<DoctorRatingDTO>> getAllDoctorsSortedByReviews(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(doctorService.getDoctorsSortedByRating(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/doctors/{id}/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable("id") Integer id,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ) {
        try {
            DoctorDTO doctorDTO = doctorService.getDoctorById(id);
            if(doctorDTO == null) {
                throw new AppException("Doctor with such id not found");
            }
            HospitalDTO hospitalDTO = hospitalService.getHospitalById(doctorDTO.getHospitalId());
            if(hospitalDTO == null) {
                throw new RuntimeException("Hospital is empty");
            }
            if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(user.getHandle(), hospitalDTO.getUserHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(reviewService.getReviewsForDoctor(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/doctor-ratings")
    public @ResponseBody ResponseEntity<Page<DoctorRatingDTO>> getDoctorRatingsPage(
            @RequestParam Integer shifts,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(doctorService.getDoctorRatingsPage(shifts, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/doctor-ratings-with-users")
    public @ResponseBody ResponseEntity<Page<DoctorRatingWithUserHandleDTO>> getDoctorRatingsWithUsersPage(
            @RequestParam Integer shifts,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(doctorService.getDoctorRatingsPageWithUsers(shifts, pageNumber, pageSize), HttpStatus.OK);
    }
}
