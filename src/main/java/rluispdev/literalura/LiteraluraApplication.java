package rluispdev.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rluispdev.literalura.main.Main;
import rluispdev.literalura.repository.AuthorRepository;
import rluispdev.literalura.repository.BookRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
@Autowired
private BookRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Autowired
	private AuthorRepository authorRepository;


	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository, authorRepository);
		main.displayMenu();
	}
}
