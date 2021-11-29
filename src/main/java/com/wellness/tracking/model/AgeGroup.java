package com.wellness.tracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellness.tracking.enums.AgeGroupType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class AgeGroup extends AbstractPersistable<Long> {

    private AgeGroupType ageGroup;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "enrollment_age_group",
            joinColumns = @JoinColumn(name = "age_group_id"),
            inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
    private Set<Enrollment> enrollments;
}
