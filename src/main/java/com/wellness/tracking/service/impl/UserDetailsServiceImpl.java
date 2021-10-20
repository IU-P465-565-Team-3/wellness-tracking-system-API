package com.wellness.tracking.service.impl;

import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.dto.mapper.UserMapper;
import com.wellness.tracking.model.Role;
import com.wellness.tracking.model.User;
import com.wellness.tracking.repository.RoleRepository;
import com.wellness.tracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
