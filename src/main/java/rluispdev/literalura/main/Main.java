package rluispdev.literalura.main;

import rluispdev.literalura.model.Author;
import rluispdev.literalura.model.Book;
import rluispdev.literalura.model.BookData;
import rluispdev.literalura.repository.AuthorRepository;
import rluispdev.literalura.repository.BookRepository;
import rluispdev.literalura.service.ConvertData;
import rluispdev.literalura.service.ManagerGutendex;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ManagerGutendex manager = new ManagerGutendex();
    private ConvertData converter = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/";
    private BookRepository repository;
    private final AuthorRepository authorRepository;
    Author author = new Author();

    public Main(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
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
                        getBook();
                        break;
                    case 2:
                        getlistBooks();
                        break;
                    case 3:
                        getlistAuthors();
                        break;
                    case 4:
                        System.out.println("Listando autores vivos em determinado ano");
                        break;
                    case 5:
                        System.out.println("Listando autores mortos em determinado ano");
                        break;

                    case 6:
                        System.out.println("Listar livros com mais de 500 downloads");
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opcão inexistente, tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
        read.close();
    }

    public void getBook() {
        String searchTerm = getSearchTerm();
        ConvertData apiResponse = getApiResponse(searchTerm);

        if (apiResponse != null && !apiResponse.getResults().isEmpty()) {
            Optional<BookData> optionalBookData = findBook(apiResponse);

            optionalBookData.ifPresentOrElse(
                    bookData -> {
                        Book book = new Book(bookData);


                        // Salvar autores usando streams
                        List<Author> authors = author.getAuhtors().stream()
                                .map(authorRepository::save) // Salva cada autor e mapeia para o autor salvo
                                .collect(Collectors.toList()); // Coleta os autores em uma lista

                        author.setAuhtors(authors);


                        repository.save(book);
                        printBookInfo(book);
                    },
                    () -> System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm)
            );
        } else {
            System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm);
        }
    }


    private void printBookInfo(Book book) {
        String bookName = book.getBookName();
        String authorName = getAuthorName(book);
        String language = getLanguageName(book.getLanguage());
        Integer downloads = book.getDownload();

        System.out.printf("""
                   _____________________
                   
                   Livro: %s
                   Autor: %s
                   Disponível em: %s
                   Nº de downloads: %d 
                   """, bookName, authorName , language, downloads);
    }
    private String getSearchTerm() {
        System.out.print("Digite o título do livro: ");
        return read.nextLine();
    }
    private ConvertData getApiResponse(String searchTerm) {
        String json = manager.getData(URL_BASE + "?search=" +
                searchTerm.replace(" ", "%20"));
        return converter.getData(json, ConvertData.class);
    }
    private Optional<BookData> findBook(ConvertData converter) {
        return converter.getResults().stream().findFirst();
    }
    private String getAuthorName(Book book) {
        return author.getAuhtors().stream()
                .findFirst()
                .map(Author::getName)
                .orElse("Unknown Author");
    }
    private String getLanguageName(String languageCode) {
        switch (languageCode) {
            case "pt":
                return "Português";
            case "en":
                return "Inglês";
            case "es":
                return "Espanhol";
            case "fr":
                return "Francês";
            case "it":
                return "Italiano";
            default:
                return languageCode;
        }
    }

    private void getlistBooks() {
        List<Book> books = repository.findAll();
        books.forEach(b -> System.out.println(b.toString()));

    }

    private void getlistAuthors() {
        List<Author> authors = authorRepository.findAll();

        authors.forEach(author -> {
            if (author.getAuhtors() != null) {
                author.getAuhtors().stream()
                        .filter(bookAuthor -> bookAuthor.getId().equals(author.getId()))
                        .findFirst()
                        .ifPresent(bookAuthor -> {
                            author.setBirth_year(bookAuthor.getBirth_year());
                            author.setDeath_year(bookAuthor.getDeath_year());
                        });
            }

            System.out.println(author.toString());
        });
    }


}


