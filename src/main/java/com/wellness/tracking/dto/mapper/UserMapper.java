package com.wellness.tracking.dto.mapper;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.model.User;

public class UserMapper {
	public static User toUser(UserDTO userDTO) {
		return new User()
				.setUsername(userDTO.getUsername())
				.setPasswordHash(userDTO.getPassword());
	}

	public static UserDTO toUserDto(User user) {
		return new UserDTO()
				.setUsername(user.getUsername());
	}

}
