package dxc.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;
    private String name;
    private Date birthday;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties("authors")
    private Set<Book> books = new HashSet<>();

    public Author() {}

    public Author(Long authorId, String name, Date birthday, Set<Book> books) {
        this.authorId = authorId;
        this.name = name;
        this.birthday = birthday;
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", books=" + books +
                '}';
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
