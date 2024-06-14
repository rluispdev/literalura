package rluispdev.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rluispdev.literalura.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}