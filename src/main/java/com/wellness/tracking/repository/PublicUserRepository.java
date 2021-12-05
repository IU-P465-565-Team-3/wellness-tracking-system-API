package com.wellness.tracking.repository;

import com.wellness.tracking.enums.Role;
import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicUserRepository extends JpaRepository<PublicUser, Long> {
	PublicUser findUserByUsername(String username);
	List<PublicUser> findByFirstNameContaining(String q);
	List<PublicUser> findUserByRole(Role role);
}
