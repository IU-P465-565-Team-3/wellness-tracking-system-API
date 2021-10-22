package com.wellness.tracking.service.impl;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.dto.mapper.UserMapper;
import com.wellness.tracking.model.Role;
import com.wellness.tracking.model.User;
import com.wellness.tracking.repository.RoleRepository;
import com.wellness.tracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleRepository.findRoleByName(userDTO.getRole());
        User user = UserMapper.toUser(userDTO);
        user.setRole(role);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public User updateUser(String username, User user) {
        User userData = userRepository.findUserByUsername(username);
        if (userData != null) {
            userData.setPasswordHash(user.getPasswordHash());
            userData.setFirstName(user.getFirstName());
            userData.setLastName(user.getLastName());
            userData.setRole(user.getRole());
        }
        userRepository.save(userData);
        return userData;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(user.getRole().getId().toString());
        authorities.add(userRole);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(),
                authorities);
    }
}
