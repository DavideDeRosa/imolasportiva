package it.imolasportiva.progetto.ut;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.test.context.TestPropertySource;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImolaSportivaApplicationTests {

	@BeforeAll
	public static void init() {
		URL propertiesDir = ImolaSportivaApplicationTests.class.getClassLoader().getResource("conf/ut");
		System.setProperty("openjdk.app.properties.dir", propertiesDir.getPath());
	}

	@Test
	void contextLoads() {
		// Il tuo test qui
	}
}

