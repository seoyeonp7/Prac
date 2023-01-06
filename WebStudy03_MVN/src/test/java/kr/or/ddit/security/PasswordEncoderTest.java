package kr.or.ddit.security;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {
	
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	String passowrd = "java";
	String mem_pass = "{bcrypt}$2a$10$rx4WeC/51QTJwVJQGPk3rOUgzBDS1sj8CI4jhhp4uBXj6.dN7UCzy";
	
	public void encodeTest() {
		String encoded = encoder.encode(passowrd);
		log.info("mem_pass : {}",encoded);
	}
	
	@Test
	public void matchTest() {
		log.info("match result : {}", encoder.matches(passowrd, mem_pass));
	}
}
