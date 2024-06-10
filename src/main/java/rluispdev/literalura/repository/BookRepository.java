package rluispdev.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rluispdev.literalura.model.Book;

public interface BookRepository extends JpaRepository <Book, Long> {
}
