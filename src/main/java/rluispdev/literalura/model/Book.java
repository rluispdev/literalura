package rluispdev.literalura.model;

import jakarta.persistence.*;
import rluispdev.literalura.dto.BookData;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String bookName;
    private String language;
    private Integer download;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() { }

    public Book(BookData bookData) {
        this.bookName = bookData.bookName();
        this.language = bookData.languages() != null && !bookData.languages().isEmpty() ? bookData.languages().get(0) : "N/A";
        this.download = bookData.download();
    }

//    public Book() { }
//
//    public Book(BookData bookData) {
//        this.bookName = bookData.bookName();
//        this.language = bookData.languages() != null && !bookData.languages().isEmpty() ? bookData.languages().get(0) : "N/A";
//        this.download = bookData.download();
//
//    //Verifica se bookData.author() retorna uma lista
//        if (bookData.author() instanceof List) {
//            List<Author> authors = (List<Author>) bookData.author();
//            // Seleciona o primeiro autor da lista, se a lista não estiver vazia
//            this.author = authors != null && !authors.isEmpty() ? authors.get(0) : null;
//        } else {
//          //   Lança uma exceção se bookData.author() não for do tipo esperado
//            throw new IllegalArgumentException("Expected a List of authors but got: " + bookData.author().getClass().getName());
//        }
//    }


    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = Integer.valueOf(download);
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return
                " • Nome do Livro: " + bookName +
                " | Disponivél em: " + language  +
                " | Nº de downloads: " + download;
    }
}