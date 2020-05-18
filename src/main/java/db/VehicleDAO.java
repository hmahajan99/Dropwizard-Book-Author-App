package db;


import core.Author;
import core.Vehicle;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class VehicleDAO extends AbstractDAO<Vehicle> {

    public VehicleDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    public List<Vehicle> findAll() {
        return list((Query<Vehicle>)namedQuery("core.Vehicle.findAll"));
    }

    public Optional<Vehicle> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Vehicle create(Vehicle vehicle){
        return persist(vehicle);
    }

}
