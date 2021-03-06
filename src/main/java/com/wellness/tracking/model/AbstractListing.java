package com.wellness.tracking.model;

import com.wellness.tracking.enums.ListingType;
import com.wellness.tracking.enums.converter.ListingTypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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
    private Boolean isPrivate;

    @Column
    private Boolean isApproved;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "listing_tag",
            joinColumns = @JoinColumn(name = "listing_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @Formula("(select avg(r.rating) from review as r where r.listing_id = id)")
    private Double averageRating;

    @CreationTimestamp
    private Date createdDate;
}
