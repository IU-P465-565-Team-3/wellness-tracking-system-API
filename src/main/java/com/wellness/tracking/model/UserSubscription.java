package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Table(name = "user_subscription")
@Entity
@Getter
@Setter
public class UserSubscription extends AbstractPersistable<Long> {

    @OneToOne(cascade = CascadeType.ALL)
    private PublicUser publisher;

    @OneToOne(cascade = CascadeType.ALL)
    private PublicUser user;
}
