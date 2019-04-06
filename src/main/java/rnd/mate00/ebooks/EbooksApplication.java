package rnd.mate00.ebooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude = JmsAutoConfiguration.class)
//@EnableJms
public class EbooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbooksApplication.class, args);
	}
}
