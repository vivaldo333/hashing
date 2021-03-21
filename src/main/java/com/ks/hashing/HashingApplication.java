package com.ks.hashing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.ks.hashing")
public class HashingApplication extends SpringBootServletInitializer {

	@SuppressWarnings("squid:S4823")
	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(HashingApplication.class)
				.profiles("dev")
				.properties("spring.profiles.active=dev")
				.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder
				.profiles("prod")
				.properties("spring.profiles.active=prod")
				.sources(HashingApplication.class);
	}

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
