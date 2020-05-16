package core;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authors")
@NamedQueries({
        @NamedQuery(name = "core.Author.findAll",
                query = "select e from Author e")
        ,
        @NamedQuery(name = "core.Author.findByName",
                query = "select e from Author e "
                        + "where e.name like :name")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public Author() {
    }

    public Author(long id, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
