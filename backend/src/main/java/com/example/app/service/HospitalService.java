package com.example.app.service;

import com.example.app.dto.HospitalDoctorCountDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.Hospital;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.model.User;
import com.example.app.repository.HospitalRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HospitalService implements IHospitalService {
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    UserRepository userRepository;
    public Page<HospitalDTO> getAllHospitals(Integer pageNumber, Integer pageSize){
        return hospitalRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(HospitalDTO::fromHospital);
    }

    public HospitalDTO getHospitalById(Integer id) throws AppException {
        Hospital hospital = hospitalRepository.findById(id).orElse(null);
        if(hospital == null){
            throw new AppException("No hospital with such id exists");
        }
        return HospitalDTO.fromHospital(hospital);
    }

    public void createHospital(HospitalDTO hospitalDTO) throws AppException {
        User user = userRepository.findById(hospitalDTO.getUserHandle()).orElse(null);
        if(user == null) {
            throw new AppException("Invalid user handle specified");
        }
        hospitalRepository.save(HospitalDTO.toHospital(hospitalDTO, user));
    }

    public void updateHospitalWithId(Integer id, HospitalDTO hospitalDTO) throws AppException {
        Hospital repoHospital = hospitalRepository.findById(id).orElse(null);
        if(repoHospital == null) {
            throw new AppException("No such hospital found in repository");
        }
        User user = userRepository.findById(hospitalDTO.getUserHandle()).orElse(null);
        if(user == null) {
            throw new AppException("Invalid user handle specified");
        }
        Hospital hospital = HospitalDTO.toHospital(hospitalDTO, user);
        hospital.setId(repoHospital.getId());
        hospitalRepository.save(hospital);
    }

    public void deleteHospitalWithId(Integer id) {
        hospitalRepository.deleteById(id);
    }

    @Override
    public Page<HospitalDoctorCountDTO> getHospitalsSortedByDoctors(Integer pageNumber, Integer pageSize) {
        return hospitalRepository
                .getHospitalsSortedByDoctorCount(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<HospitalDoctorCountDTO> getHospitalDoctorCountsPage(Integer pageNumber, Integer pageSize) {
        Page<HospitalDTO> hospitalDTOPage = hospitalRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(HospitalDTO::fromHospital);

        return new PageImpl<>(
                hospitalRepository.getHospitalDoctorCountsFromList(hospitalDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                hospitalDTOPage.getTotalElements()
        );
    }

}
