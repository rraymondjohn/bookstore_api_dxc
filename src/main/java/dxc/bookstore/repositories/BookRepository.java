package dxc.bookstore.repositories;

import dxc.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b JOIN b.authors a " +
            "WHERE (:title IS NULL OR b.title = :title) " +
            "AND (:authorName IS NULL OR a.name = :authorName)")
    List<Book> findByTitleAndOrAuthorName(@Param("title") String title, @Param("authorName") String authorName);



//    List<Book> findByAuthors_Name(String authorName);
//    List<Book> findByTitle(String title);
}
