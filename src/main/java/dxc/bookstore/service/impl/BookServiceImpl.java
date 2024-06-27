package dxc.bookstore.service.impl;

import dxc.bookstore.entities.Author;
import dxc.bookstore.entities.Book;
import dxc.bookstore.exception.BookExistException;
import dxc.bookstore.exception.BookNotFoundException;
import dxc.bookstore.repositories.AuthorRepository;
import dxc.bookstore.repositories.BookRepository;
import dxc.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    //retrieve all books
    @Override
    public List<Book> getAllBooks() {
        log.info("BookServiceImpl: getAllBooks");

        return bookRepository.findAll();
    }

    //add a book
    @Override
    public Book addBook(Book book) {
        log.info("BookServiceImpl: addBook");
        Optional<Book> existingBook = bookRepository.findById(book.getIsbn());
        if(existingBook.isPresent()) {
            log.error("Book already exists with ISBN: {}", book.getIsbn());
            throw new BookExistException(book.getIsbn());
        }

        return bookRepository.save(book);
    }

    //update a book
    @Override
    public Book updateBook(String isbn, Book updatedBook) {
        log.info("BookServiceImpl: updateBook");
        Optional<Book> existingBook = bookRepository.findById(isbn);

        if(existingBook.isPresent()) {
            log.info("Updating book with ISBN: {}", isbn);
            Book currentBook = existingBook.get();
            currentBook.setTitle(updatedBook.getTitle());
            currentBook.setAuthors(updatedBook.getAuthors());
            currentBook.setYear(updatedBook.getYear());
            currentBook.setPrice(updatedBook.getPrice());
            currentBook.setGenre(updatedBook.getGenre());

            return bookRepository.save(currentBook);
        } else {
            log.error("No existing book with ISBN: {}", isbn);

            throw new BookNotFoundException(isbn);
        }
    }

    //retrieve books based on search
    @Override
    public List<Book> searchBooksByTitleOrAuthor(String title, String author) {
        log.info("BookServiceImpl: getBookByTitleOrAuthor");

        return bookRepository.findByTitleAndOrAuthorName(title, author);
    }

    //delete a book
    @Override
    @Transactional
    public void deleteBook(String isbn) {
        log.info("BookServiceImpl: deleteBook");
        Optional<Book> existingBook = bookRepository.findById(isbn);
        if(existingBook.isPresent()) {
            //set authors to new set for later checking and deletion
            //clear authors from book
            Set<Author> authors = new HashSet<>(existingBook.get().getAuthors());
            existingBook.get().getAuthors().clear();

            bookRepository.delete(existingBook.get());

            //check if authors have any books, if so delete author
            for(Author author : authors) {
                if(author.getBooks().size() - 1 == 0) { //remove 1 book due to deletion of existingBook
                    log.info("Author {} with ID {} has no books, removing author from database", author.getName(), author.getAuthorId());

                    authorRepository.delete(author);
                }
            }
        } else {
            log.error("Book not found with ISBN: {}", isbn);
            throw new BookNotFoundException(isbn);
        }


    }
}
