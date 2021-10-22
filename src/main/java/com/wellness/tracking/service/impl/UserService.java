package com.wellness.tracking.service.impl;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.model.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    User updateUser(String username, User user);
}
