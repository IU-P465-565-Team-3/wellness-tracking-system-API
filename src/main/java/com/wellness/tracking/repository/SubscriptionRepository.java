package com.wellness.tracking.repository;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByPublisher(PublicUser publisher);
}