package com.wellness.tracking.model;

import com.wellness.tracking.enums.AgeGroupType;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Table(name = "app_user")
@Entity
@Getter
@Setter
public class User extends AbstractUser {

    @Column
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Type(type = "postgres_enum")
    private AgeGroupType ageGroup;
}
