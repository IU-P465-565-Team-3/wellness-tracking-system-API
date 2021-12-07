package com.wellness.tracking.repository;

import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.ListingSummary_;
import org.springframework.data.jpa.domain.Specification;

public class ListingSummarySpecifications {
    public static Specification<ListingSummary> listingNameOrDescriptionContains(String q) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get(ListingSummary_.name), "%" + q + "%"),
                criteriaBuilder.like(root.get(ListingSummary_.description), "%" + q + "%")));
    }
}
