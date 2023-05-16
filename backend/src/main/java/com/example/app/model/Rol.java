package com.example.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="rol")
public class Rol {
    @Getter
    @Setter
    @Id
    private String name;
    @Getter
    @Setter
    private boolean read_all;
    @Getter
    @Setter
    private boolean update_all;
    @Getter
    @Setter
    private boolean delete_all;
    @Getter
    @Setter
    private boolean read_own;
    @Getter
    @Setter
    private boolean update_own;
    @Getter
    @Setter
    private boolean delete_own;
    @Getter
    @Setter

    private boolean creat;
}
