package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Doctor;
import com.example.app.model.Review;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.model.User;
import com.example.app.repository.DoctorRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ReviewService implements  IReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Page<ReviewDTO> getReviewsForUser(String handle, Integer pageNumber, Integer pageSize) throws AppException {
        Optional<User> user = userRepository.findById(handle);
        if(user.isEmpty()){
            throw new AppException("No such user exists");
        }
        return reviewRepository
                .findAllByUser(user.get(), PageRequest.of(pageNumber, pageSize))
                .map(ReviewDTO::fromReview);
    }

    @Override
    public Page<ReviewDTO> getReviewsForDoctor(Integer id, Integer pageNumber, Integer pageSize) throws AppException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            throw new AppException("No such doctor exists");
        }
        return reviewRepository
                .findAllByDoctor(doctor.get(), PageRequest.of(pageNumber, pageSize))
                .map(ReviewDTO::fromReview);
    }

    @Override
    public ReviewDTO getReview(String handle, Integer doctorId) throws AppException {
        Optional<Review> review = reviewRepository.findById(new ReviewId(handle, doctorId));
        if(review.isEmpty()) {
            throw  new AppException("No such review exists");
        }
        return ReviewDTO.fromReview(review.get());
    }

    @Override
    public void createReview(ReviewDTO reviewDTO) throws AppException {
        User user = userRepository.findById(reviewDTO.getUserHandle()).orElse(null);
        if(user == null) {
            throw new AppException("No such user exists");
        }
        Doctor doctor = doctorRepository.findById(reviewDTO.getDoctorId()).orElse(null);
        if(doctor == null) {
            throw new AppException("No such doctor exists");
        }
        reviewRepository.save(ReviewDTO.toReview(reviewDTO, user, doctor));
    }

    @Override
    public void updateReview(String handle, Integer doctorId, ReviewDTO reviewDTO) throws AppException {
        User user = userRepository.findById(handle).orElse(null);
        if(user == null) {
            throw new AppException("No such user exists");
        }
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if(doctor == null) {
            throw new AppException("No such doctor exists");
        }
        Review review = ReviewDTO.toReview(reviewDTO, user, doctor);
        review.setId(new ReviewId(handle, doctorId));
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(String handle, Integer doctorId) {
        reviewRepository.deleteById(new ReviewId(handle, doctorId));
    }

}
