package com.wellness.tracking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "app_user")
@Entity
@Getter
@Setter
public class PublicUser extends AbstractUser {
}
