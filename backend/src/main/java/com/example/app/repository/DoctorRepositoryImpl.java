package com.example.app.repository;

import com.example.app.dto.DoctorRatingDTO;
import com.example.app.dto.model.DoctorDTO;
import com.example.app.model.Hospital;
import com.example.app.model.Doctor;
import com.example.app.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorRepositoryImpl implements IDoctorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<DoctorRatingDTO> getDoctorsSortedByAverageRating(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> review_ratings_cq = cb.createQuery(Tuple.class);
        Root<Review> review = review_ratings_cq.from(Review.class);
        review_ratings_cq
                .multiselect(review.get("doctor").get("id").alias("doctor_id"), cb.avg(cb.coalesce(review.get("rating"), 0)).alias("rating"))
                .groupBy(review.get("doctor").get("id"))
                .orderBy(cb.desc(cb.avg(cb.coalesce(review.get("rating"), 0))));

        TypedQuery<Tuple> typedQuery = em.createQuery(review_ratings_cq);

        List<DoctorRatingDTO> results = typedQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map( row -> {
                        CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);
                        Root<Doctor> doctor = cq.from(Doctor.class);
                        cq
                                .select(doctor)
                                .where(cb.equal(doctor.get("id"), row.get("doctor_id")));

                        return new DoctorRatingDTO(
                                DoctorDTO.fromDoctor(em.createQuery(cq).getSingleResult()),
                                (Double)row.get("rating")
                        );
                    }
                )
                .toList();

        CriteriaQuery<Long> count_cq = cb.createQuery(Long.class);
        Root<Review> review_count = count_cq.from(Review.class);
        count_cq.select(cb.countDistinct(review_count.get("doctor").get("id")));
        long total = em.createQuery(count_cq).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<DoctorRatingDTO> getDoctorRatingsFromList(List<DoctorDTO> doctorDTOS) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> review_ratings_cq = cb.createQuery(Tuple.class);
        Root<Review> review = review_ratings_cq.from(Review.class);
        CriteriaBuilder.In<Integer> inClause = cb.in(review.get("doctor").get("id"));
        for(DoctorDTO doctorDTO : doctorDTOS) {
            inClause.value(doctorDTO.getId());
            System.out.println(doctorDTO.getId());
        }
        review_ratings_cq
                .multiselect(review.get("doctor").get("id").alias("doctor_id"), cb.avg(cb.coalesce(review.get("rating"), 0)).alias("rating"))
                .groupBy(review.get("doctor").get("id"))
                .where(inClause);

        List<Tuple> typedQueryResult = em.createQuery(review_ratings_cq).getResultList();

        return doctorDTOS.stream()
                .map(doctorDTO -> {
                    Double rating = typedQueryResult.stream()
                            .filter(row -> row.get("doctor_id").equals(doctorDTO.getId()))
                            .map(row -> (Double)row.get("rating"))
                            .findFirst()
                            .orElse(0.0);
                    return new DoctorRatingDTO(doctorDTO, rating);
                }).collect(Collectors.toList());
    }

    @Override
    public Integer getDoctorCountForUserHandle(String userHandle) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Doctor> doctor = cq.from(Doctor.class);
        Join<Hospital, Doctor> doctorHospitalJoin = doctor.join("hospital", JoinType.LEFT);
        cq
                .select(cb.count(doctorHospitalJoin))
                .where(cb.equal(doctorHospitalJoin.get("user").get("handle"), userHandle));

        TypedQuery<Long> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult().intValue();
    }
}
