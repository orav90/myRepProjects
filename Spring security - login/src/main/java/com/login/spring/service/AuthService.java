package com.login.spring.service;

import com.login.spring.exceptions.TokenVerificationException;
import com.login.spring.exceptions.UserExistsException;
import com.login.spring.model.*;
import com.login.spring.model.SignupRequest;
import com.login.spring.repository.RoleRepository;
import com.login.spring.repository.UserRepository;
import com.login.spring.repository.VerificationTokenRepository;
import com.login.spring.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    private final static int HOURS = 1;

    public void signup(SignupRequest signupRequest) throws UserExistsException{

        {
            if (userRepository.existsByUsername(signupRequest.getUsername()) || userRepository.existsByEmail(signupRequest.getEmail())) {
                throw new UserExistsException(String.format("Username/mail %s already exists",signupRequest.getUsername()));
            }

            // Create new user's account
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setUsername(signupRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setActive(false);

            Set<String> strRoles = signupRequest.getRoles();
            Set<Role> roles = new HashSet<>();
            System.out.println(strRoles);


            for(String r : strRoles){
                switch (r){
                    case "admin":
                        Role adminRole = roleRepository.findByType(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleRepository.findByType(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                    default:
                        throw new RuntimeException("Not supported");
                }
            }

            user.setRoles(roles);
            userRepository.save(user);
            String verificationToken = generateVerificationToken(user);
            mailService.sendMail(new RegistrationMail(user.getUsername(),user.getEmail(),"Please Activate your account",verificationToken));
        }
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .username(loginRequest.getUsername())
                .expiresAt(ZonedDateTime.now().plusWeeks(2))
                .build();
    }

    public String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiresAt(LocalDateTime.now().plusHours(HOURS));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void validateUser(String token) {

        Optional<VerificationToken> verifiedToken = verificationTokenRepository.findByToken(token);
        if(verifiedToken.get() == null || verifiedToken.get().getExpiresAt().isBefore(LocalDateTime.now())){
            throw new TokenVerificationException("Token is null or expired");
        }
        verificationTokenRepository.deleteByToken(token);

        User user = userRepository.findById(verifiedToken.get().getUser().getId()).orElseThrow(
                ()-> new TokenVerificationException("Can't find user from token "+ token));
        user.setActive(true);
        userRepository.save(user);

    }

}
