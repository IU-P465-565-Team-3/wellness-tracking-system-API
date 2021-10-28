package com.wellness.tracking.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.STRING)
public class Listing extends AbstractListing {
}
