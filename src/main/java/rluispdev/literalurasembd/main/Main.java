package rluispdev.literalurasembd.main;

import rluispdev.literalurasembd.model.Author;
import rluispdev.literalurasembd.model.Book;
import rluispdev.literalurasembd.model.BookData;
import rluispdev.literalurasembd.service.ApiResponse;
import rluispdev.literalurasembd.service.ConvertData;
import rluispdev.literalurasembd.service.ManagerGutendex;

import java.util.Scanner;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ManagerGutendex manager = new ManagerGutendex();
    private ConvertData converter = new ConvertData();


    private final String URL_BASE = "https://gutendex.com/books/";

    public void displayMenu() {
        var option = -1;

        while (option != 0) {
            var menu = """
                    ---------------
                    Escolha o número de sua opção:
                    1 - buscar livro pelo título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    0 - sair
                                    
                    """;
            System.out.println(menu);
            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    getBook();
                    break;
                case 2:
                    System.out.println("listando livros");
                    break;
                case 3:
                    System.out.println("Listando autores");
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
                    System.out.println("Opcão inválida, tente novamente.");
                    continue;
            }

        }
    }


    private void getBook() {
        System.out.print("Enter the search term: ");
        String searchTerm = read.nextLine();
        String json = manager.getData( URL_BASE + "?search=" + searchTerm.replace(" ", "%20"));
        // Converter a string JSON em um objeto ApiResponse
        ApiResponse apiResponse = converter.getData(json, ApiResponse.class);

        // Verificar se há resultados na ApiResponse
        // Verificar se há resultados na ApiResponse
        if (apiResponse != null && apiResponse.getResults() != null && !apiResponse.getResults().isEmpty()) {
            // Iterar sobre os resultados (assumindo que você quer lidar com o primeiro livro encontrado)
            BookData firstBook = apiResponse.getResults().get(0);
            // Criar um objeto Book a partir dos dados do primeiro livro
            Book book = new Book(firstBook);
            // Imprimir os detalhes do livro
            System.out.println("Book Name: " + book.getBookName());

            // Verifique se a lista de autores não é nula e não está vazia
            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                System.out.println("Autor : " + book.getAuthors().get(0).getName()); // Supondo que você esteja interessado apenas no primeiro autor
            } else {
                System.out.println("Autor : Unknown Author");
            }
            System.out.println("Disponivel: " + book.getLanguage());
        } else {
            System.out.println("No books found for the search term: " + searchTerm);
        }
    }
}





