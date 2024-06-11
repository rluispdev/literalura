package rluispdev.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rluispdev.literalura.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}