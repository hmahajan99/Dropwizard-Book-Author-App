package db;


import core.Author;
import core.Employee;
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

    public Optional<Author> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Author create(Author author){
        return persist(author);
    }

}
