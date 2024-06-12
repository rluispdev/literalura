package rluispdev.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private int birth_year;
    private int death_year;

    @ManyToMany
    @JoinTable(name = "author_collaboration",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "collaborator_id"))
    private List<Author> collaborators = new ArrayList<>();

    public Author() { }

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

    public List<Author> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Author> collaborators) {
        this.collaborators = collaborators;
    }

    @Override
    public String toString() {
        return
                "Nome do Author: " + name + '\'' +
                ", Ano de Nascimento: " + birth_year +
                ", Ano de Falecimento: " + death_year;
    }
}
