package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;
    @NotBlank(message = "Hospital name is mandatory")
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private Date registerDate;
    @OneToMany(mappedBy="hospital", fetch=FetchType.LAZY)
    @Getter
    @Setter
    List<Doctor> doctors;
    @Getter
    @Setter
    @Column(name = "user_handle", insertable=false, updatable = false)
    private String userHandle;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_handle", nullable = false)
    @Getter
    @Setter
    private User user;

}
