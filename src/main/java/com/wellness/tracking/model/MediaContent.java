package com.wellness.tracking.model;

import com.wellness.tracking.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MediaContent extends AbstractPersistable<Long> {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Type(type = "postgres_enum")
    private ContentType type;

    @Column
    private String description;

    @Column
    private String sourceUrl;

    @Column
    private String annotation;
}
