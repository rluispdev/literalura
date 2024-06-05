package rluispdev.literalurasembd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rluispdev.literalurasembd.main.Main;

@SpringBootApplication
public class LiteralurasembdApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteralurasembdApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.displayMenu();
	}
}
