package br.com.siteware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@SpringBootApplication
public class SitewareECommerceApplication {

	@GetMapping
	public String getHome() {
		return "Siteware e-commerce - API Home";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SitewareECommerceApplication.class, args);
	}

}
