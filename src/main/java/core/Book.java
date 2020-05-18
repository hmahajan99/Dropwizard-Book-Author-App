package core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "core.Book.findAll",
                query = "select b from Book b")
        ,
        @NamedQuery(name = "core.Book.findByName",
                query = "select b from Book b "
                        + "where b.name like :name ")
})

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
//    @Column(name = "author")
//    private String author;

    @ManyToMany
    @JsonIgnoreProperties("books")
    private Set<Author> authors = new HashSet<Author>();


    public Book() {
    }

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
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


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Book book = (Book) o;
//        return id == book.id &&
//                name.equals(book.name) &&
//                author.equals(book.author);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, author);
//    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
