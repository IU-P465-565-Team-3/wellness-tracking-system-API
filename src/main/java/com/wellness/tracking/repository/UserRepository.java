package com.wellness.tracking.repository;

import com.wellness.tracking.model.ListingSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wellness.tracking.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
	List<User> findByFirstNameContaining(String q);
}
