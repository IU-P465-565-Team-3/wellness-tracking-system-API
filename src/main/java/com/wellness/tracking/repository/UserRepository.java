package com.wellness.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellness.tracking.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
}
