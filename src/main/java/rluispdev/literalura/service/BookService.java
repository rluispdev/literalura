package rluispdev.literalura.service;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

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

    // Método principal para buscar livros por título
    public void getBook() {
        String searchTerm = getSearchTerm();
        ConvertData apiResponse = getApiResponse(searchTerm);

        if (apiResponse != null && !apiResponse.getResults().isEmpty()) {
            apiResponse.getResults().stream()
                    .findFirst()
                    .ifPresentOrElse(
                            this::processBookData,
                            () -> System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm)
                    );
        } else {
            System.out.println("Nenhum livro encontrado para o termo de pesquisa: " + searchTerm);
        }
    }

    // Processa os dados do livro obtidos da API
    private void processBookData(BookData bookData) {
        List<AuthorData> authors = bookData.authors();

        if (authors.isEmpty()) {
            Author author = getOrCreateAuthor(new AuthorData("Sem informação do autor", null, null));
            Book book = createBook(bookData, author);
            confirmAndSaveBook(book);
        } else {
            authors.stream()
                    .map(this::getOrCreateAuthor)
                    .map(author -> createBook(bookData, author))
                    .forEach(this::confirmAndSaveBook);
        }
    }

    // Obtém ou cria um autor com base nos dados recebidos
    private Author getOrCreateAuthor(AuthorData authorData) {
        String name = Optional.ofNullable(authorData.name())
                .filter(n -> !n.isEmpty())
                .orElse("Sem informação do autor");

        return authorRepository.findByName(name)
                .orElseGet(() -> {
                    Author newAuthor = new Author(authorData);
                    authorRepository.save(newAuthor);
                    return newAuthor;
                });
    }

    // Cria um livro com base nos dados recebidos e associa ao autor
    private Book createBook(BookData bookData, Author author) {
        Book book = new Book(bookData);
        book.setAuthor(author);
        return book;
    }

    // Confirma e salva o livro no repositório
    public void confirmAndSaveBook(Book book) {
        String bookName = book.getBookName();
        String authorName = book.getAuthor() != null ? book.getAuthor().getName() : "Unknown Author";

        bookRepository.findByBookNameAndAuthor_Name(bookName, authorName)
                .ifPresentOrElse(
                        existingBook -> {
                            System.out.println("Livro encontrado no banco de dados com o mesmo título e autor:");
                            printBookInfo(existingBook);
                            System.out.println("Este livro já está registrado no banco de dados.");
                        },
                        () -> {
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
                );
    }

    // Salva o livro no repositório
    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    // Imprime informações detalhadas do livro
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

    // Obtém o termo de pesquisa do usuário
    private String getSearchTerm() {
        System.out.print("Digite o título do livro: ");
        return read.nextLine();
    }

    // Obtém a resposta da API com base no termo de pesquisa
    private ConvertData getApiResponse(String searchTerm) {
        String json = manager.getData(URL_BASE + "?search=" + searchTerm.replace(" ", "%20"));
        return converter.getData(json, ConvertData.class);
    }

    // Converte o código do idioma para o nome do idioma
    private String getLanguageName(String languageCode) {
        switch (languageCode.toLowerCase()) {
            case "pt":
            case "portugues":
            case "português":
                return "pt";
            case "en":
            case "ingles":
            case "inglês":
                return "en";
            case "es":
            case "espanhol":
                return "es";
            case "fr":
            case "frances":
            case "francês":
                return "fr";
            case "it":
            case "italiano":
                return "it";
            case "de":
            case "alemao":
            case "alemão":
                return "de";
            default:
                return languageCode;
        }
    }

    // Obtém informações resumidas do livro para listagem
    private String getBookInfo(Book book) {
        String bookName = book.getBookName();
        String language = getLanguageName(book.getLanguage());

        return String.format(" • Livro: %s | Disponível em: %s", bookName, language);
    }

    // Obtém todos os livros registrados no banco de dados
    public void getAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            System.out.println("Nenhum livro registrado no banco de dados.");
        } else {
            String formattedBooks = books.stream()
                    .map(this::getBookInfo)
                    .collect(Collectors.joining("\n"));
            System.out.println(formattedBooks);
        }
    }

    // Obtém todos os autores dos livros registrados no banco de dados
    public void getAllAuthors() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            System.out.println("Nenhum livro registrado no banco de dados.");
        } else {
            Set<Author> uniqueAuthors = new HashSet<>();
            books.stream()
                    .map(Book::getAuthor)
                    .filter(author -> author != null)
                    .forEach(uniqueAuthors::add);

            if (uniqueAuthors.isEmpty()) {
                System.out.println("Nenhum autor registrado no banco de dados.");
            } else {
                String formattedAuthors = uniqueAuthors.stream()
                        .map(this::getAuthorInfo)
                        .collect(Collectors.joining("\n"));
                System.out.println(formattedAuthors);
            }
        }
    }


    // Obtém informações resumidas do autor para listagem
    private String getAuthorInfo(Author author) {
        return " • Autor(a): " + author.getName();
    }

    // Obtém os autores vivos em um determinado ano
    public List<Author> getAuthorsAliveInYear() {
        System.out.println("Para ver os autores vivos em determinado ano, digite o ano: ");
        int year;
        try {
            year = read.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            read.next(); // Limpa a entrada inválida
            return Collections.emptyList();
        }

        if (year < 0) {
            System.out.println("Ano inválido. Por favor, insira um ano válido.");
            return Collections.emptyList();
        }

        int currentYear = Year.now().getValue(); // Obtém o ano atual

        if (year > currentYear) {
            System.out.println("Você digitou um ano muito no futuro. Por favor, insira um ano até " + currentYear + ".");
            return Collections.emptyList();
        }

        // Buscar o menor ano de nascimento presente no banco de dados
        Integer minBirthYear = authorRepository.findMinBirthYear();

        if (minBirthYear == null) {
            System.out.println("Não há registros no banco de dados.");
            return Collections.emptyList();
        }

        if (year <= minBirthYear) {
            System.out.println("Você digitou um ano histórico. Por favor, insira um ano a partir de " + (minBirthYear + 1) + ".");
            return Collections.emptyList();
        }

        List<Author> authors = authorRepository.findAuthorsAliveInYear(year);

        // Verifica se encontrou autores e imprime cada um deles
        if (authors == null || authors.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo no ano " + year);
        } else {
            System.out.println("Autores vivos no ano " + year + ":");
            boolean foundAuthor = false;
            for (Author author : authors) {
                if (author.getBirth_year() != 0) { // Verifica se o ano de nascimento não é zero
                    System.out.println(getAuthorInfo(author)); // Usa o método getAuthorInfo para imprimir informações do autor
                    foundAuthor = true;
                }
            }
            if (!foundAuthor) {
                System.out.println("Nenhum autor encontrado vivo no ano " + year);
            }
        }
        return authors;
    }


// Busca livros pelo idioma (opção ignorando case)
    public List<Book> findBooksByLanguage(String language) {
        String languageCode = getLanguageName(language);
        return bookRepository.findBooksByLanguageIgnoreCase(languageCode);
    }

}
