package com.wellness.tracking.model;

import com.wellness.tracking.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractUser extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Type(type = "postgres_enum")
    protected Role role;

    @Column(unique = true)
    protected String username;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    private String gender;

    @Column
    private Date dateOfBirth;
}
