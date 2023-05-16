package com.example.app.model;

import com.example.app.model.Ids.ReviewId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @EmbeddedId
    @Getter
    @Setter
    ReviewId id = new ReviewId();
    @MapsId("userHandle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_handle", nullable=false)
    @Getter
    @Setter
    private User user;

    @MapsId("doctorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    @Getter
    @Setter
    private Doctor doctor;
    @Getter
    @Setter
    private Integer rating;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Date postedDate;
}
