package rluispdev.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rluispdev.literalura.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookNameAndAuthor_Name(String bookName, String authorName);

    @Query("SELECT b FROM Book b WHERE UPPER(REPLACE(b.language, 'á', 'a')) = UPPER(REPLACE(:language, 'á', 'a')) " +
            "OR UPPER(REPLACE(b.language, 'é', 'e')) = UPPER(REPLACE(:language, 'é', 'e')) " +
            "OR UPPER(REPLACE(b.language, 'ê', 'e')) = UPPER(REPLACE(:language, 'ê', 'e')) " +
            "OR UPPER(REPLACE(b.language, 'ó', 'o')) = UPPER(REPLACE(:language, 'ó', 'o')) " +
            "OR UPPER(REPLACE(b.language, 'ô', 'o')) = UPPER(REPLACE(:language, 'ô', 'o')) " +
            "OR UPPER(REPLACE(b.language, 'ã', 'a')) = UPPER(REPLACE(:language, 'ã', 'a')) " +
            "OR UPPER(REPLACE(b.language, 'â', 'a')) = UPPER(REPLACE(:language, 'â', 'a')) " +
            "OR UPPER(REPLACE(b.language, 'í', 'i')) = UPPER(REPLACE(:language, 'í', 'i')) " +
            "OR UPPER(REPLACE(b.language, 'ú', 'u')) = UPPER(REPLACE(:language, 'ú', 'u')) " +
            "OR UPPER(REPLACE(b.language, 'õ', 'o')) = UPPER(REPLACE(:language, 'õ', 'o')) " +
            "OR UPPER(REPLACE(b.language, 'ç', 'c')) = UPPER(REPLACE(:language, 'ç', 'c'))")
    List<Book> findBooksByLanguageIgnoreCase(@Param("language") String language);


}
