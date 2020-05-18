package db;

import core.Book;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;
import java.util.Optional;

public class BookDAO extends AbstractDAO<Book>{
    public BookDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    public List<Book> findAll() {
        return list((Query<Book>)namedQuery("core.Book.findAll"));
    }

    public List<Book> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list((Query<Book>)namedQuery("core.Book.findByName")
                .setParameter("name", builder.toString())
        );
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Book create(Book book){
        return persist(book);
    }
}
