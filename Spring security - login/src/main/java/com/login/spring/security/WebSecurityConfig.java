package com.login.spring.security;

import com.login.spring.security.jwt.JwtAuthFilter;
import com.login.spring.security.jwt.JwtConfiguration;
import com.login.spring.security.jwt.JwtUsernamePasswordAuthFilter;
import com.login.spring.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthFilter jwtAuthFilter;



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(new JwtUsernamePasswordAuthFilter(authenticationManager()))
				.addFilterBefore(jwtAuthFilter, JwtUsernamePasswordAuthFilter.class)
				.authorizeRequests()
				.antMatchers("/","/api/auth/**")
				.permitAll()
				.anyRequest()
				.authenticated();

		//http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public JwtConfiguration authenticationJwtTokenFilter() {
		return new JwtConfiguration();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
