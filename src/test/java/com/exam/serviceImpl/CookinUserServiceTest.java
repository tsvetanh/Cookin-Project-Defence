package com.exam.serviceImpl;


import com.exam.model.entities.User;
import com.exam.model.entities.UserRole;
import com.exam.model.entities.emuns.Role;
import com.exam.repository.UserRepository;
import com.exam.service.impl.CookinUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class CookinUserServiceTest {

  private CookinUserService serviceToTest;

  @Mock
  UserRepository mockUserRepository;

  @BeforeEach
  public void setUp() {
    serviceToTest = new CookinUserService(mockUserRepository);
  }

  @Test
  void testUserNotFound() {
    Assertions.assertThrows(
        UsernameNotFoundException.class, () -> {
          serviceToTest.loadUserByUsername("non-existing-user");
        }
    );
  }

  @Test
  void testExistingUser() {
    User user = new User();
    user.setUsername("pesho");
    user.setPassword("asdasd");

    UserRole roleUser = new UserRole();
    roleUser.setRole(Role.USER);
    UserRole roleAdmin = new UserRole();
    roleAdmin.setRole(Role.ADMIN);

    user.setRoles(List.of(roleUser, roleAdmin));



    Mockito.when(mockUserRepository.findByUsername("pesho")).thenReturn(Optional.of(user));



    UserDetails userDetails = serviceToTest.loadUserByUsername("pesho");

    Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
    Assertions.assertEquals(2, userDetails.getAuthorities().size());

    List<String> authorities = userDetails.
        getAuthorities().
        stream().
        map(GrantedAuthority::getAuthority).
        collect(Collectors.toList());

    Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
    Assertions.assertTrue(authorities.contains("ROLE_USER"));
  }

}
