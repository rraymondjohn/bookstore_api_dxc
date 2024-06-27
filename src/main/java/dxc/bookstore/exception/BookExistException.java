package dxc.bookstore.exception;

public class BookExistException extends RuntimeException {
    public BookExistException(String isbn) {
        super("Book already exists with ISBN: " + isbn);
    }
}
