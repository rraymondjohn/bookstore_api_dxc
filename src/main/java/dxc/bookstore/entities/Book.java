package dxc.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "Book")
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String isbn;
    private String title;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "bookAuthor",
            joinColumns = @JoinColumn(name = "bookIsbn"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    @JsonIgnoreProperties("books")
    private Set<Author> authors = new HashSet<>();
    private int year;
    private double price;
    private String genre;

    public Book() {}

    public Book(String isbn, String title, Set<Author> authors, int year, double price, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", year=" + year +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
