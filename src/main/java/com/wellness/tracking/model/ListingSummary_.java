package com.wellness.tracking.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@StaticMetamodel(ListingSummary.class)
public class ListingSummary_ {
    public static volatile SingularAttribute<ListingSummary, String> name;
    public static volatile SingularAttribute<ListingSummary, String> description;
    public static volatile SingularAttribute<ListingSummary, PublicUser> user;
    public static volatile SingularAttribute<ListingSummary, List<Tag>> tags;
}
