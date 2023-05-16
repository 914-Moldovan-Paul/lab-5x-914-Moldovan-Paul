package com.example.app.service;

import com.example.app.dto.DoctorRatingDTO;
import com.example.app.dto.DoctorRatingWithUserHandleDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.Hospital;
import com.example.app.model.Doctor;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.dto.model.DoctorDTO;
import com.example.app.repository.HospitalRepository;
import com.example.app.repository.DoctorRepository;
import com.example.app.repository.specification.DoctorSpecifications;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class DoctorService implements IDoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    HospitalRepository hospitalRepository;

    public Page<DoctorDTO> getAllDoctors(Integer pageNumber, Integer pageSize){
        return doctorRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(DoctorDTO::fromDoctor);
    }

    public DoctorDTO getDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor == null){
            return null;
        }
        return DoctorDTO.fromDoctor(doctor);
    }

    public void createDoctor(DoctorDTO doctorDTO) throws AppException {
        Optional<Hospital> hospital = hospitalRepository.findById(doctorDTO.getHospitalId());
        if(hospital.isEmpty()) {
            throw new AppException("Hospital not found");
        }

        doctorRepository.save(DoctorDTO.toDoctor(doctorDTO, hospital.get()));
    }

    public void updateDoctorWithId(Integer id, DoctorDTO doctorDTO) throws AppException {
        Doctor repoDoctor = doctorRepository.findById(id).orElse(null);
        if(repoDoctor == null) {
            throw new AppException("No such doctor exists");
        }
        Hospital hospital = hospitalRepository.findById(doctorDTO.getHospitalId()).orElse(null);
        if(hospital == null) {
            throw new AppException("No such hospital exists");
        }
        Doctor doctor = DoctorDTO.toDoctor(doctorDTO, hospital);
        doctor.setId(repoDoctor.getId());
        doctorRepository.save(doctor);
    }

    public void deleteDoctorWithId(Integer id) {
        doctorRepository.deleteById(id);
    }

    public Page<DoctorDTO> getAllDoctorsWithShiftsBiggerThan(Integer shifts, Integer pageNumber, Integer pageSize){
        return doctorRepository
                .findAll(DoctorSpecifications.shiftsBiggerThan(shifts), PageRequest.of(pageNumber, pageSize))
                .map(DoctorDTO::fromDoctor);
    }

    @Override
    public HospitalDTO getHospitalByDoctorId(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor == null){
            return null;
        }
        return HospitalDTO.fromHospital(doctor.getHospital());
    }

    @Override
    public Page<DoctorDTO> getDoctorsByHospitalId(Integer id, Integer pageNumber, Integer pageSize) throws AppException {
        Hospital hospital = hospitalRepository.findById(id).orElse(null);
        if(hospital == null){
            throw new AppException("No such hospital exists");
        }
        return doctorRepository
                .findAllByHospital(hospital, PageRequest.of(pageNumber, pageSize))
                .map(DoctorDTO::fromDoctor);
    }

    @Override
    public Page<DoctorRatingDTO> getDoctorsSortedByRating(Integer pageNumber, Integer pageSize) {
        return doctorRepository
                .getDoctorsSortedByAverageRating(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<DoctorRatingDTO> getDoctorRatingsPage(Integer shifts, Integer pageNumber, Integer pageSize) {
        Page<DoctorDTO> doctorDTOPage = doctorRepository
                .findAll(DoctorSpecifications.shiftsBiggerThan(shifts), PageRequest.of(pageNumber, pageSize))
                .map(DoctorDTO::fromDoctor);
        return new PageImpl<>(
                doctorRepository.getDoctorRatingsFromList(doctorDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                doctorDTOPage.getTotalElements()
        );
    }

    @Override
    public Page<DoctorRatingWithUserHandleDTO> getDoctorRatingsPageWithUsers(Integer shifts, Integer pageNumber, Integer pageSize) {
        return this.getDoctorRatingsPage(shifts, pageNumber, pageSize)
                .map(doctorRatingDTO -> {
                    return new DoctorRatingWithUserHandleDTO(
                            doctorRatingDTO,
                            hospitalRepository.findById(doctorRatingDTO.getDoctorDTO().getHospitalId()).get().getUserHandle()
                    );
                });
    }
}

