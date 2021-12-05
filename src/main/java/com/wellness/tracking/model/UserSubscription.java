package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "user_subscription")
@Entity
@Getter
@Setter
public class UserSubscription extends AbstractPersistable<Long> {

    @Column
    private PublicUser publisher;

    @Column
    private PublicUser user;
}
