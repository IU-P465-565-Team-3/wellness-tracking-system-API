package com.wellness.tracking.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "app_user")
@Entity
@Getter
@Setter
public class User extends AbstractUser {

    @Column
    private String passwordHash;

    @Column
    private String gender;

    @Column
    private Date dateOfBirth;
}
