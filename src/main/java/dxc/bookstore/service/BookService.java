package dxc.bookstore.service;

import dxc.bookstore.entities.Book;
import dxc.bookstore.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {

    //retrieve all books
    List<Book> getAllBooks();

    //add a book
    Book addBook(Book book);

    //update a book
    Book updateBook(String isbn, Book updatedBook);

    //retrieve books based on search
    List<Book> searchBooksByTitleOrAuthor(String title, String author);

    //delete a book
    void deleteBook(String isbn);
}
