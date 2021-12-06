package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Subscription extends AbstractPersistable<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SUBSCRIPTION_PRODUCER"))
    private PublicUser producer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SUBSCRIPTION_CONSUMER"))
    private PublicUser consumer;

    @CreatedDate
    private Date createdAt;
}
