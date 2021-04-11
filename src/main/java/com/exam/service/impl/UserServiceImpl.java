package com.exam.service.impl;

import com.exam.model.entities.User;
import com.exam.model.entities.UserRole;
import com.exam.model.entities.emuns.Role;
import com.exam.model.service.UserServiceModel;
import com.exam.repository.UserRepository;
import com.exam.repository.UserRoleRepository;
import com.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public final ModelMapper modelMapper;
    private CookinUserService cookinUserService;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CookinUserService cookinUserService, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cookinUserService = cookinUserService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void register(UserServiceModel userServiceModel, boolean admin) {
        User newUser = modelMapper.map(userServiceModel, User.class);
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        UserRole userRole = userRoleRepository.
                findByRole(Role.USER).orElseThrow(
                () -> new IllegalStateException("USER role not found. Please seed the roles."));

        newUser.setRoles(new ArrayList<>());
        newUser.addRole(userRole);

        if(admin) {
            UserRole adminRole = userRoleRepository.
                    findByRole(Role.ADMIN).orElseThrow(
                    () -> new IllegalStateException("ADMIN role not found. Please seed the roles."));
            newUser.addRole(adminRole);
        }


        newUser = userRepository.save(newUser);

        UserDetails principal = cookinUserService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean login(UserServiceModel userServiceModel) {
        User user = userRepository.getUserByUsername(userServiceModel.getUsername());


        if(passwordEncoder.matches(userServiceModel.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }
    @Override
    public UserServiceModel findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserServiceModel findById(Long id) {
        return modelMapper.map(userRepository.getById(id), UserServiceModel.class);
    }
}
