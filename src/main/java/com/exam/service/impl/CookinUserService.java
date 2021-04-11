package com.exam.service.impl;

import com.exam.repository.UserRepository;
import org.hibernate.NonUniqueResultException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CookinUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public CookinUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NonUniqueResultException {
            com.exam.model.entities.User user = userRepository.
                    findByUsername(username).
                    orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " was not found!"));
            return mapToUserDetails(user);

    }

    private UserDetails mapToUserDetails(com.exam.model.entities.User user) {
        List<GrantedAuthority> authorities = user.
                getRoles().
                stream().
                map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole())).
                collect(Collectors.toList());

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}
