package com.example.app.dto.model;

import com.example.app.model.Doctor;
import com.example.app.model.Review;
import com.example.app.model.User;
import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReviewDTO {
    @Getter
    @Setter
    private String userHandle;
    @Getter
    @Setter
    private Integer doctorId;
    @Getter
    @Setter
    private Integer rating;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Date postedDate;

    public static ReviewDTO fromReview(Review review) {
        return new ReviewDTO(
                review.getUser().getHandle(),
                review.getDoctor().getId(),
                review.getRating(),
                review.getDescription(),
                review.getPostedDate()
        );
    }

    public static Review toReview(ReviewDTO reviewDTO, User user, Doctor doctor) {
        Review review = new Review();
        review.setUser(user);
        review.setDoctor(doctor);
        review.setRating(reviewDTO.getRating());
        review.setDescription(reviewDTO.getDescription());
        review.setPostedDate(reviewDTO.getPostedDate());
        return review;
    }
}
