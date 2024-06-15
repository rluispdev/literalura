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
    private Integer birth_year;
    private Integer death_year;

    public Author() { }

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birth_year = authorData.birth_year() != null ? authorData.birth_year() : 0;
        this.death_year = authorData.death_year() != null ? authorData.death_year() : 0;
    }

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

    public Integer getBirth_year() {
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

    @Override
    public String toString() {
        return
                "Nome do Author: " + name + '\'' +
                ", Ano de Nascimento: " + birth_year +
                ", Ano de Falecimento: " + death_year;
    }


}
