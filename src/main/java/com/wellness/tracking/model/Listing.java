package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Listing extends AbstractListing {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "listing_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_LISTING"))
    @OrderBy("createdDate desc")
    private Collection<Review> reviews;
}
