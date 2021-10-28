package com.wellness.tracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.wellness.tracking.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String gender;

	@NotNull
	private Date dateOfBirth;

	@NotNull
	private Role role;
}
