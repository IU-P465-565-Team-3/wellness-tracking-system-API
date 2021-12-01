package com.wellness.tracking.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Enrollment extends AbstractPersistable<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLMENT_USER"))
    private PublicUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "listing_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLMENT_LISTING"))
    private FitnessPlan plan;

    @Column
    private Date startDate;

    @Column
    private Double rating;

    @ManyToMany
    @JoinTable(
            name = "enrollment_age_group",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "age_group_id")
    )
    private Set<AgeGroup> ageGroupSet;
}
