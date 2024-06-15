package rluispdev.literalura.main;

import java.util.List;
import java.util.Scanner;

import rluispdev.literalura.model.Book;
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
                        bookService.getAllBooks();
                        break;
                    case 3:
                      bookService.getAllAuthors();
                        break;
                    case 4:
                        bookService.getAuthorsAliveInYear();

                        break;
                    case 5:
                        searchBooksByLanguage();
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

    public void searchBooksByLanguage() {
        System.out.println("Digite o idioma dos livros que deseja buscar:");
        String language = read.nextLine();

        List<Book> books = bookService.findBooksByLanguage(language);

        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma " + language);
        } else {
            System.out.println("Livros encontrados no idioma " + language + ":");
            for (Book book : books) {
                System.out.println(book.getBookName());
            }
        }
    }
}