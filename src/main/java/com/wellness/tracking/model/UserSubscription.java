package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "user_subscription")
@Entity
@Getter
@Setter
public class UserSubscription extends AbstractUser {


}
