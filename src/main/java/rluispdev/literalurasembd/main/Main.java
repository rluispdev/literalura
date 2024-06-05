package rluispdev.literalurasembd.main;

import rluispdev.literalurasembd.service.ConvertData;
import rluispdev.literalurasembd.service.ManagerGutendex;

import java.util.Scanner;

public class Main {
    private Scanner read = new Scanner(System.in);
    private ManagerGutendex manager = new ManagerGutendex();
    private ConvertData convert = new ConvertData();
    String address;

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
        System.out.println("Digite o nome de um livro: ");
        var bookName = read.nextLine();
        address = URL_BASE + "?search=" + bookName.replace(" ", "%20");
        var json = manager.getData(address);
        System.out.println(json);


    }
}


