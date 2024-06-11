package rluispdev.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)



    private Long id;

    private String name;
    private int birth_year;
    private int death_year;

    @ManyToOne
    private Book book;

    public Author() { }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year; // Corrigido para atribuir corretamente o valor passado
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year; // Corrigido para atribuir corretamente o valor passado
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return
                "Nome do Author: " + name + '\'' +
                ", Ano de Nascimento: " + birth_year +
                ", Ano de Falecimento: " + death_year;
    }
}
