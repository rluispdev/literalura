package rluispdev.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rluispdev.literalura.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookNameAndAuthor_Name(String bookName, String authorName);
}
