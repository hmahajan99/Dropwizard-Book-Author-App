package db;


import core.Author;
import core.Book;
import core.Employee;
import core.Vehicle;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AuthorDAO  extends AbstractDAO<Author> {

    public AuthorDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    public List<Author> findAll() {
        return list((Query<Author>)namedQuery("core.Author.findAll"));
    }

    public List<Author> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list((Query<Author>)namedQuery("core.Author.findByName")
                .setParameter("name", builder.toString())
        );
    }

    public Author addVehicle(Author author, Vehicle vehicle) {
        author.getVehicles().add(vehicle);
        vehicle.setOwner(author);
        return author;
    }

    public Author addBook(Author author, Book book) {
        author.getBooks().add(book);
        book.getAuthors().add(author);
        return author;
    }


    public Optional<Author> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Author create(Author author){
        return persist(author);
    }

}
