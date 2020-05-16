package db;


import core.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO  extends AbstractDAO<Employee> {

    public EmployeeDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    public List<Employee> findAll() {
        return list((Query<Employee>)namedQuery("core.Employee.findAll"));
    }

    public List<Employee> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list((Query<Employee>)namedQuery("core.Employee.findByName")
                .setParameter("name", builder.toString())
        );
    }

    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Employee create(Employee employee){
        return persist(employee);
    }

}
