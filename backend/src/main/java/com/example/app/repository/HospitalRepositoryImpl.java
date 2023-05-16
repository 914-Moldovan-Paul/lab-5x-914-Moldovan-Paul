package com.example.app.repository;

import com.example.app.dto.HospitalDoctorCountDTO;
import com.example.app.dto.model.HospitalDTO;
import com.example.app.model.Hospital;
import com.example.app.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HospitalRepositoryImpl implements IHospitalRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<HospitalDoctorCountDTO> getHospitalsSortedByDoctorCount(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Hospital> hospital = cq.from(Hospital.class);
        Join<Hospital, Doctor> hospitalDoctorJoin = hospital.join("doctors", JoinType.LEFT);
        cq
                .multiselect(hospital, cb.count(hospitalDoctorJoin))
                .groupBy(hospital)
                .orderBy(cb.desc(cb.count(hospitalDoctorJoin)));
        TypedQuery<Object[]> typedQuery = em.createQuery(cq);


        List<HospitalDoctorCountDTO> results = typedQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(row -> {
                    return new HospitalDoctorCountDTO(
                            HospitalDTO.fromHospital((Hospital) row[0]),
                            ((Long) row[1]).intValue()
                    );
                })
                .toList();

        CriteriaQuery<Long> count_cq = cb.createQuery(Long.class);
        count_cq.select(cb.count(count_cq.from(Hospital.class)));;
        long total = em.createQuery(count_cq).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<HospitalDoctorCountDTO> getHospitalDoctorCountsFromList(List<HospitalDTO> hospitalDTOS) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Doctor> doctor = cq.from(Doctor.class);
        CriteriaBuilder.In<Integer> inClause = cb.in(doctor.get("hospital").get("id"));
        for(HospitalDTO hospitalDTO : hospitalDTOS) {
            inClause.value(hospitalDTO.getId());
        }
        cq
                .multiselect(doctor.get("hospital").get("id").alias("id"), cb.count(doctor.get("id")).alias("count"))
                .groupBy(doctor.get("hospital").get("id"))
                .where(inClause);

        List<Tuple> typedQueryResult = em.createQuery(cq).getResultList();

        return hospitalDTOS.stream()
                .map(hospitalDTO -> {
                    Integer count = typedQueryResult.stream()
                            .filter(row -> row.get("id").equals(hospitalDTO.getId()))
                            .map(row -> (Long)row.get("count"))
                            .map(Long::intValue)
                            .findFirst()
                            .orElse(0);
                    return new HospitalDoctorCountDTO(hospitalDTO, count);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Integer getHospitalCountForUserHandle(String userHandle) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Hospital> hospitals = cq.from(Hospital.class);

        cq
                .select(cb.count(hospitals))
                .where(cb.equal(hospitals.get("user").get("handle"), userHandle));
        TypedQuery<Long> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult().intValue();
    }
}
