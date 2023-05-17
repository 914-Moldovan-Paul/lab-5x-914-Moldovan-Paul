package com.example.app.controller;

import com.example.app.dto.HospitalDoctorCountDTO;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.dto.model.DoctorDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IHospitalService;
import com.example.app.service.IDoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Validated
@CrossOrigin
public class HospitalController {
    @Autowired
    private IHospitalService hospitalService;
    @Autowired
    private IDoctorService doctorService;

    @GetMapping(path="/hospitals")
    public @ResponseBody ResponseEntity<Page<HospitalDTO>> getHospitals(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user")
            User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(hospitalService.getAllHospitals(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/hospitals/sorted-by-doctors")
    public @ResponseBody ResponseEntity<Page<HospitalDoctorCountDTO>> getHospitalsSortedByDoctors(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user")
            User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(hospitalService.getHospitalsSortedByDoctors(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/hospitals/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<HospitalDTO> getHospital(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        HospitalDTO hospitalDTO = null;
        try {
            hospitalDTO = hospitalService.getHospitalById(id);
            if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(hospitalDTO, HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/hospitals/{id}/doctors", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<DoctorDTO>> getHospitalDoctors(
            @PathVariable("id") Integer id,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ) {
        HospitalDTO hospitalDTO = null;
        try {
            hospitalDTO = hospitalService.getHospitalById(id);
            if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(doctorService.getDoctorsByHospitalId(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path="/hospitals", produces = "application/json")
    public ResponseEntity<Map<String, String> > createHospital(
            @Valid @RequestBody HospitalDTO hospitalDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();

        if(!user.getUserRol().isCreat()) {
            response.put("error", "Unauthorized to create resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            hospitalDTO.setUserHandle(user.getHandle());
            hospitalService.createHospital(hospitalDTO);
            response.put("message", "Hospital created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/hospitals/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> updateHospital(
            @PathVariable("id") Integer id,
            @Valid @RequestBody HospitalDTO hospitalDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            HospitalDTO oldHospitalDTO = hospitalService.getHospitalById(id);
            if(!user.getUserRol().isUpdate_all() && (!user.getUserRol().isUpdate_own() || !Objects.equals(oldHospitalDTO.getUserHandle(), user.getHandle()))) {
                response.put("error", "Unauthorized to update resource");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            if(!user.getUserRol().isUpdate_all() && !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle())) {
                response.put("error", "Unauthorized to transfer resource between users");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            hospitalService.updateHospitalWithId(id, hospitalDTO);
            response.put("message", "Hospital updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/hospitals/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteHospital(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            HospitalDTO hospitalDTO = hospitalService.getHospitalById(id);
            if (!user.getUserRol().isDelete_all() && (!user.getUserRol().isDelete_own() || !Objects.equals(hospitalDTO.getUserHandle(), user.getHandle()))) {
                response.put("error", "Unauthorized to delete resource");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            hospitalService.deleteHospitalWithId(id);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        response.put("message", "Hospital deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping(path="/hospitals/{id}/doctors", produces = "application/json")
//    public void moveDoctorsToHospitals(
//            @PathVariable("id") Integer id,
//            @Valid @RequestBody List<Integer> doctor_ids
//    ) {
//        doctor_ids.forEach(doctor_id -> {
//            DoctorDTO doctorDTO = doctorService.getDoctorById(doctor_id);
//            doctorDTO.setHospitalId(id);
//            try {
//                doctorService.updateDoctorWithId(doctor_id, doctorDTO);
//            } catch (AppException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }

    @GetMapping(path="/hospital-doctor-counts")
    public @ResponseBody ResponseEntity<Page<HospitalDoctorCountDTO>> getHospitalDoctorCountsPage(
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
        return new ResponseEntity<>(hospitalService.getHospitalDoctorCountsPage(pageNumber, pageSize), HttpStatus.OK);
    }
}
