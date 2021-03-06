package com.wellness.tracking.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Date;
import java.util.List;
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
    private Listing listing;

    @Column
    private Date startDate;
}
