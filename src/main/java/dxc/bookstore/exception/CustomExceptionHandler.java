package dxc.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BookExistException.class)
    public ResponseEntity<Object> bookExistException(BookExistException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        body.put("status", HttpStatus.CONFLICT);
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundException(BookNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
