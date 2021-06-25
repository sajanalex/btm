package com.breakthemould.service;




import static org.hamcrest.CoreMatchers.not;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserDetailServiceImplTest {

	@Test
	void generate_encrypted_password() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "password";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
	}

}
