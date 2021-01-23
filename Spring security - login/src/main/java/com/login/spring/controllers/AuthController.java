package com.login.spring.controllers;

import javax.validation.Valid;
import com.login.spring.exceptions.TokenVerificationException;
import com.login.spring.exceptions.UserExistsException;
import com.login.spring.model.AuthenticationResponse;
import com.login.spring.model.LoginRequest;
import com.login.spring.model.SignupRequest;
import com.login.spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public AuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		return authService.login(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		try{
			authService.signup(signUpRequest);
		}
		catch (UserExistsException e){
			return ResponseEntity
					.badRequest()
					.body("Error, Username/mail is already exist");
		}

		return ResponseEntity.ok("User registered successfully");
	}

	@GetMapping("/verifyAccount/{token}")
	public ResponseEntity<String> validateRegisteredUser(@PathVariable String token){
		try{
			authService.validateUser(token);
		}
		catch (TokenVerificationException e){
			return ResponseEntity
					.badRequest()
					.body("Error during verification token validation");
		}
		return new ResponseEntity<>("User activated successfully", HttpStatus.OK);
	}
}
