package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Subscription extends AbstractPersistable<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SUBSCRIPTION_PRODUCER"))
    private PublicUser producer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SUBSCRIPTION_CONSUMER"))
    private PublicUser consumer;

    @CreationTimestamp
    private Timestamp createdDate;
}
