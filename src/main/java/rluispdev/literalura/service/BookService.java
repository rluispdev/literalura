package rluispdev.literalura.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import rluispdev.literalura.dto.AuthorData;
import rluispdev.literalura.dto.BookData;
import rluispdev.literalura.model.Author;
import rluispdev.literalura.model.Book;
import rluispdev.literalura.repository.AuthorRepository;
import rluispdev.literalura.repository.BookRepository;


public class BookService {
    private final ManagerGutendex manager = new ManagerGutendex();
    private final ConvertData converter = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final Scanner read = new Scanner(System.in);

    public BookService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void getBook() {
        String searchTerm = getSearchTerm();
        ConvertData apiResponse = getApiResponse(searchTerm);

        if (apiResponse != null && !apiResponse.getResults().isEmpty()) {
            Optional<BookData> optionalBookData = findBook(apiResponse);

            optionalBookData.ifPresentOrElse(
                    bookData -> processBookData(bookData),
                    () -> System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm)
            );
        } else {
            System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm);
        }
    }

    private void processBookData(BookData bookData) {
        List<AuthorData> authors = bookData.authors();
        if (authors.isEmpty()) {
            Author author = getOrCreateAuthor(new AuthorData("Sem informação do autor", null, null));
            Book book = createBook(bookData, author);
            confirmAndSaveBook(book);
        } else {
            for (AuthorData authorData : authors) {
                Author author = getOrCreateAuthor(authorData);
                Book book = createBook(bookData, author);
                confirmAndSaveBook(book);
            }
        }
    }

    private Author getOrCreateAuthor(AuthorData authorData) {
        String name = authorData.name();
        Integer birthYear = authorData.birth_year();
        Integer deathYear = authorData.death_year();

        if (name == null || name.isEmpty()) {
            name = "Sem informação do autor";
        }

        Author author = authorRepository.findByName(name).orElse(null);

        if (author == null) {
            author = new Author(authorData);
            authorRepository.save(author);
        }

        return author;
    }

    private Book createBook(BookData bookData, Author author) {
        Book book = new Book(bookData);
        book.setAuthor(author);
        return book;
    }

    public void confirmAndSaveBook(Book book) {
        String bookName = book.getBookName();
        String authorName = book.getAuthor() != null ? book.getAuthor().getName() : "Unknown Author";

        Optional<Book> existingBook = bookRepository.findByBookNameAndAuthor_Name(bookName, authorName);

        if (existingBook.isPresent()) {
            System.out.println("Livro encontrado no banco de dados com o mesmo título e autor:");
            printBookInfo(existingBook.get());
            System.out.println("Este livro já está registrado no banco de dados.");
            return;
        }

        System.out.println("Novo livro encontrado:");
        printBookInfo(book);

        System.out.print("Deseja salvar este livro? (s/n): ");
        String response = read.nextLine();

        if (response.equalsIgnoreCase("s")) {
            saveBook(book);
            System.out.println("Livro salvo com sucesso!");
        } else {
            System.out.println("Livro descartado.");
        }
    }

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    private void printBookInfo(Book book) {
        String bookName = book.getBookName();
        String authorName = book.getAuthor() != null ? book.getAuthor().getName() : "Unknown Author";
        String language = getLanguageName(book.getLanguage());
        Integer downloads = book.getDownload();

        System.out.printf("""
               _____________________
               
               Livro: %s
               Autor: %s
               Disponível em: %s
               Nº de downloads: %d 
               """, bookName, authorName, language, downloads);
    }

    private String getSearchTerm() {
        System.out.print("Digite o título do livro: ");
        return read.nextLine();
    }

    private ConvertData getApiResponse(String searchTerm) {
        String json = manager.getData(URL_BASE + "?search=" + searchTerm.replace(" ", "%20"));
        return converter.getData(json, ConvertData.class);
    }

    private Optional<BookData> findBook(ConvertData converter) {
        return converter.getResults().stream().findFirst();
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
}
