package com.wellness.tracking.model;

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
public class Tag extends AbstractPersistable<Long> {
    private String name;

    @ManyToMany
    @JoinTable(
        name = "listing_tag",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private Set<Listing> listings;
}
