package com.javatechie;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // Utilise le fichier application-test.properties
@SpringBootTest
public class IdentityServiceApplicationTests {

	@Test
	void contextLoads() {
		// Votre logique de test ici
	}
}
