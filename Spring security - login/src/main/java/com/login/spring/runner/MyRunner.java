package com.login.spring.runner;

import com.google.common.collect.Sets;
import com.login.spring.model.Role;
import com.login.spring.model.RoleType;
import com.login.spring.model.User;
import com.login.spring.repository.RoleRepository;
import com.login.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class MyRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private List<Role> roles;


    @Override
    public void run(String... args) throws Exception {

        List<User> users = Arrays.asList(
                new User("Donny","sdfdsa@gmail.com",passwordEncoder.encode("password"),Sets.newHashSet(new Role(RoleType.ROLE_USER))),
                new User("Budd","sdfdsa@gmail.com","passwordEncoder.enc",Sets.newHashSet(new Role(RoleType.ROLE_USER))),
                new User("Guy","sdfdsa@gmail.com","passwordEnc",Sets.newHashSet(new Role(RoleType.ROLE_USER)))
        );

        roles = Arrays.asList(
                new Role(RoleType.ROLE_USER),
                new Role(RoleType.ROLE_MODERATOR),
                new Role(RoleType.ROLE_ADMIN)
        );

        roleRepository.deleteAll();
        roleRepository.insert(roles);

        userRepository.deleteAll();
        userRepository.insert(users);
    }

}
