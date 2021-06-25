package com.breakthemould.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.breakthemould.jwt.filter.JwtRequestFilter;
import com.breakthemould.service.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(getPasswordEncoder());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors();
		
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/*").permitAll()
			.antMatchers("/swagger-ui/*").permitAll()
			.antMatchers("/v3/api-docs").permitAll()
			.antMatchers("/v3/api-docs/*").permitAll()
			.antMatchers("/pas-sport/").permitAll()
			.antMatchers("/pas-sport/home").permitAll()
			.antMatchers("/pas-sport/child/login").permitAll()
			.antMatchers("/pas-sport/child/reset_password").permitAll()
			.antMatchers("/pas-sport/child/confirm_reset").permitAll()
			.antMatchers("/pas-sport/child/register").permitAll()
			.antMatchers("/pas-sport/child/register/*").permitAll()
			.antMatchers("/pas-sport/child/activate").permitAll()
			.antMatchers("/pas-sport/child/remind_username").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			
			
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	
	

}
