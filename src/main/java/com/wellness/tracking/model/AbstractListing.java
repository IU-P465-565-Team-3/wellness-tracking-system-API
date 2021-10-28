package com.wellness.tracking.model;

import com.wellness.tracking.enums.ListingType;
import com.wellness.tracking.enums.converter.ListingTypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractListing extends AbstractPersistable<Long> {
    @Enumerated(EnumType.STRING)
    @Column(name = "type_id", nullable = false, insertable = false, updatable = false)
    @Convert(converter = ListingTypeConverter.class)
    @Type(type = "postgres_enum")
    private ListingType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", nullable = false, foreignKey = @ForeignKey(name = "FK_LISTING_PUBLISHER"))
    private PublicUser user;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private String imageAnnotation;

    @Column
    private boolean isPrivate;
}