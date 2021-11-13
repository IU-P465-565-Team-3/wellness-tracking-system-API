package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Event extends AbstractPersistable<Long> {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Column
    private String metadata;
}
