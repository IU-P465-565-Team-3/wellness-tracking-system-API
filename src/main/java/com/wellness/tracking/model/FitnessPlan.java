package com.wellness.tracking.model;

import com.wellness.tracking.enums.ListingType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = ListingType.Constants.FITNESS_PLAN)
public class FitnessPlan extends Listing {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "listing_id", nullable = false, foreignKey = @ForeignKey(name = "FK_EVENT_LISTING"))
    private Collection<Event> events;
}
