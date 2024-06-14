package rluispdev.literalura.model;

import jakarta.persistence.*;
import rluispdev.literalura.dto.AuthorData;
//import rluispdev.literalura.dto.AuthorData;


@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private int birth_year;
    private int death_year;

    public Author() { }

    public Author(String name, int birth_year, int death_year) {
        this.name = name;
        this.birth_year = birth_year;
        this.death_year = death_year;
    }

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birth_year = authorData.birth_year();
        this.death_year = authorData.death_year();
    }


//    public Author(String name, int birthYear, int deathYear) {
//        this.name = name;
//        this.birthYear = birthYear;
//        this.deathYear = deathYear;
//    }


//    public Author(AuthorData authorData){
//        this.name = authorData.name();
//        this.birth_year = authorData.birth_year();
//        this.death_year = authorData.death_year();
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

//    public List<Author> getAuthors() {
//        return author;
//    }
//
//    public void setAuthors(List<Author> authors) {
//        this.author = authors;
//    }

    @Override
    public String toString() {
        return
                "Nome do Author: " + name + '\'' +
                ", Ano de Nascimento: " + birth_year +
                ", Ano de Falecimento: " + death_year;
    }


}
