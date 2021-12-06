package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Review extends AbstractPersistable<Long> {
    private Integer rating;

    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_USER"))
    private PublicUser user;

    @Column(name = "listing_id", insertable = false, updatable = false)
    private Long listingId;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;
}
