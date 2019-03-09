package rnd.mate00.ebooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication//(exclude = SecurityAutoConfiguration.class)
@EnableJms
public class EbooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbooksApplication.class, args);
	}
}
