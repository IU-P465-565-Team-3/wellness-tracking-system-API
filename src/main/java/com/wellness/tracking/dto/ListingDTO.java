package com.wellness.tracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wellness.tracking.enums.ListingType;
import com.wellness.tracking.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingDTO<PK extends java.io.Serializable> {
    private PK id;

    @NotNull
    private ListingType type;

    @NotNull
    private PublicUser user;

    @NotNull
    private String name;

    private String description;

    private String imageUrl;

    private String imageAnnotation;

    private Boolean isPrivate;

    private Boolean isApproved;

    private Set<Tag> tags;

    private Collection<Event> events;

    private Collection<MediaContent> content;

    private Double averageRating;

    private Collection<Review> reviews;
}
