package it.imolasportiva.progetto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ImolaSportivaApplication {

	public static void main(String[] args) {
		log.info("Avvio applicazione.");

		SpringApplication.run(ImolaSportivaApplication.class, args);
	}

}
