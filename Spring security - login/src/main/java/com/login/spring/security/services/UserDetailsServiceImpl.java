package com.login.spring.security.services;

import com.login.spring.model.User;
import com.login.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	//@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =
				userRepository.findByUsername(username)
				.orElseThrow(() ->
						new UsernameNotFoundException(String.format("User with username %s was not found " , username)));

		return UserDetailsImpl.build(user);
	}

}
