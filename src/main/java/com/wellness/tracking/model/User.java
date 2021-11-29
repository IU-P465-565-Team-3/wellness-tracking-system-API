package com.wellness.tracking.model;

import com.wellness.tracking.enums.AgeGroupType;
import lombok.*;
import org.hibernate.annotations.Type;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Type(type = "postgres_enum")
    private AgeGroupType ageGroup;
}
