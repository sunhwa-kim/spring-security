package com.sunhwa.demospringsecurity;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSecurityApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 다양한 패스워드 인코딩 지원  default bcript
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
