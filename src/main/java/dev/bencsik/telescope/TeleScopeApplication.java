package dev.bencsik.telescope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TeleScopeApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeleScopeApplication.class, args);
	}
}