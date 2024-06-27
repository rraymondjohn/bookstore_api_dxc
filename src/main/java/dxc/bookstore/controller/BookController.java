package dxc.bookstore.controller;

import dxc.bookstore.entities.Book;
import dxc.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/api/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        log.info("BookController: Get all books API");

        return bookService.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        log.info("BookController: Add book API");
        log.info(book.toString());

        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book updatedbook) {
        log.info("BookController: Update book API");

        return ResponseEntity.ok(bookService.updateBook(isbn, updatedbook));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBook(@RequestParam String title, @RequestParam String author) {
        log.info("BookController: Search book API");

        return ResponseEntity.ok(bookService.searchBooksByTitleOrAuthor(title,author));
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        log.info("BookController: Delete book API");
        bookService.deleteBook(isbn);

        return ResponseEntity.ok("Book has been deleted");
    }
}
