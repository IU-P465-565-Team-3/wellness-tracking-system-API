package com.wellness.tracking.repository;

import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicUserRepository extends JpaRepository<PublicUser, Long> {
	PublicUser findUserByUsername(String username);
}
