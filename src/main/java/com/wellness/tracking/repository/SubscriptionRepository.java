package com.wellness.tracking.repository;

import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByConsumer(PublicUser consumer);
    List<Subscription> findAllByProducer(PublicUser producer);
    Long countAllByProducer(PublicUser producer);
}
