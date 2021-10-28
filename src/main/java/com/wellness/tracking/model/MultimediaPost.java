package com.wellness.tracking.model;

import com.wellness.tracking.enums.ListingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@DiscriminatorValue(ListingType.Constants.MULTIMEDIA_POST)
public class MultimediaPost extends Listing {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "listing_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MEDIA_LISTING"))
    private Collection<MediaContent> content;
}
