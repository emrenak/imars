package login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
(scanBasePackages={"com.imars.core.service","login"})
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
