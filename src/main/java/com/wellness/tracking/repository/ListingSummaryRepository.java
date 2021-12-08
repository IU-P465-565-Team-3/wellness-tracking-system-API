package com.wellness.tracking.repository;

import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ListingSummaryRepository extends JpaRepository<ListingSummary, Long>, JpaSpecificationExecutor<ListingSummary> {
    List<ListingSummary> findAllByIsPrivateIsFalse();
    List<ListingSummary> findByUser(PublicUser user);
    List<ListingSummary> findAllByIdInAndIsPrivateIsFalse(Collection<Long> id);
    List<ListingSummary> findByUserInAndIdNotInAndIsPrivateIsFalse(Collection<PublicUser> publishers, Collection<Long> listingIds);
    List<ListingSummary> findByIdInAndIdNotInAndIsPrivateIsFalse(Collection<Long> includeIds, Collection<Long> excludeIds);
    List<ListingSummary> findByIdInAndIsPrivateIsFalse(Collection<Long> includeIds);
    List<ListingSummary> findByNameContainingOrDescriptionContainingAndIsPrivateIsFalse(String name, String description);
    List<ListingSummary> findByUserInAndIsPrivateIsFalse(List<PublicUser> publishers);
    List<ListingSummary> findByTagsInAndIsPrivateIsFalse(List<Tag> tags);
    List<ListingSummary> findByNameContainingOrDescriptionContainingAndUserInAndIsPrivateIsFalse(String name, String description, List<PublicUser> publishers);
    List<ListingSummary> findByNameContainingOrDescriptionContainingAndTagsInAndIsPrivateIsFalse(String name, String description, List<Tag> tags);
    List<ListingSummary> findByUserInAndTagsInAndIsPrivateIsFalse(List<PublicUser> publishers, List<Tag> tags);
    List<ListingSummary> findByNameContainingAndUserInAndTagsInAndIsPrivateIsFalse(String name, List<PublicUser> publishers, List<Tag> tags);
}
