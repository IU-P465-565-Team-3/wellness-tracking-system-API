package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Review extends AbstractPersistable<Long> {
    private Integer rating;

    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_USER"))
    private PublicUser user;

    @JoinColumn(name = "listing_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_LISTING"))
    private Long listingId;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;
}
