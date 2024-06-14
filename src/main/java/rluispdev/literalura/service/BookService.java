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

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ManagerGutendex manager = new ManagerGutendex();
    private final ConvertData converter = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/";

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
        for (AuthorData authorData : bookData.authors()) {
            Author author = getOrCreateAuthor(authorData);
            Book book = createBook(bookData, author);
            saveBook(book);
            printBookInfo(book);
        }
    }

    private Author getOrCreateAuthor(AuthorData authorData) {
        return authorRepository.findByName(authorData.name())
                .orElseGet(() -> {
                    Author author = new Author(authorData);
                    return authorRepository.save(author);
                });
    }

    private Book createBook(BookData bookData, Author author) {
        Book book = new Book(bookData);
        book.setAuthor(author);
        return book;
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
               """, bookName, authorName , language, downloads);
    }

    private String getSearchTerm() {
        Scanner read = new Scanner(System.in);
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

    public void getlistBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(b -> System.out.println(b.toString()));
    }
}
