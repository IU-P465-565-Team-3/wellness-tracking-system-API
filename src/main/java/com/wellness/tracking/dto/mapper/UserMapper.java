package com.wellness.tracking.dto.mapper;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.model.User;

import java.util.stream.Collectors;

public class UserMapper {
	public static User toUser(UserDTO userDTO) {
		return new User()
				.setUsername(userDTO.getUsername())
				.setPassword(userDTO.getPassword());
	}

	public static UserDTO toUserDto(User user) {
		return new UserDTO()
				.setUsername(user.getUsername())
				.setRoleName(user.getRoles().stream()
						.map( n -> n.getRoleName())
						.collect( Collectors.joining( "," ) ));
	}

}
