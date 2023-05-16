package com.example.app.model.Ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ReviewId implements Serializable {
    @Column(name = "user_handle")
    public String userHandle;
    @Column(name = "doctor_id")
    public Integer doctorId;

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(userHandle, reviewId.userHandle) && Objects.equals(doctorId, reviewId.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userHandle, doctorId);
    }
}
