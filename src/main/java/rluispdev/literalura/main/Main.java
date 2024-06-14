package rluispdev.literalura.main;

import java.util.Scanner;
import rluispdev.literalura.service.BookService;
import rluispdev.literalura.repository.AuthorRepository;
import rluispdev.literalura.repository.BookRepository;

public class Main {
    private Scanner read = new Scanner(System.in);
    private BookService bookService;

    public Main(BookRepository repository, AuthorRepository authorRepository) {
        this.bookService = new BookService(authorRepository, repository);
    }

    public void displayMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    _____________________

                    Escolha o número de sua opção:

                    1 - Pesquisar livro
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    0 - sair

                    """;
            System.out.println(menu);

            try {
                option = Integer.parseInt(read.nextLine());

                switch (option) {
                    case 1:
                        bookService.getBook();
                        break;
                    case 2:

                        break;
                    case 3:
                        // Implementar a lógica para listar autores registrados
                        break;
                    case 4:
                        System.out.println("Listando autores vivos em determinado ano");
                        // Implementar a lógica para listar autores vivos em um determinado ano
                        break;
                    case 5:
                        System.out.println("Listando livros em um determinado idioma");
                        // Implementar a lógica para listar livros em um determinado idioma
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inexistente, tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
        read.close();
    }

}