package com.example.app.repository;

import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Doctor;
import com.example.app.model.Review;
import com.example.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId>, JpaSpecificationExecutor<Review>, IReviewRepository {
    Page<Review> findAllByUser(User user, PageRequest pageable);
    Page<Review> findAllByDoctor(Doctor doctor, PageRequest pageable);
}
