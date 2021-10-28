package com.wellness.tracking.dto.mapper;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.model.User;

public class UserMapper {
	public static User toUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPasswordHash(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setGender(userDTO.getGender());
		user.setDateOfBirth(userDTO.getDateOfBirth());
		return user;
	}

	public static UserDTO toUserDto(User user) {
		return new UserDTO()
				.setUsername(user.getUsername())
				.setRole(user.getRole())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());
	}
}
