package com.wellness.tracking.service.impl;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.dto.mapper.UserMapper;
import com.wellness.tracking.model.Role;
import com.wellness.tracking.model.User;
import com.wellness.tracking.repository.RoleRepository;
import com.wellness.tracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
        userRepository.save(UserMapper.toUser(userDTO));
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        Role role = roleRepository.findRoleByRoleName(userDTO.getRoleName());
        user.getRoles().add(role);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Collection<SimpleGrantedAuthority> userRoles = user.getRoles().stream()
                .map( n -> new SimpleGrantedAuthority(n.getRoleName()))
                .collect( Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                userRoles);
    }
}
