package core;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "core.Book.findAll",
                query = "select b from Book b")
        ,
        @NamedQuery(name = "core.Book.findByName",
                query = "select b from Book b "
                        + "where b.name like :name "),
        @NamedQuery(name = "core.Book.findByAuthor",
                query = "select b from Book b "
                        + "where b.author like :author ")

})

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;

    public Book() {
    }

    public Book(long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                name.equals(book.name) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author);
    }
}
