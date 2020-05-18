package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Author;
import core.Vehicle;
import db.AuthorDAO;
import db.VehicleDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResources {

    private VehicleDAO vehicleDAO;

    public VehicleResources(VehicleDAO vehicleDAO){
        this.vehicleDAO = vehicleDAO;
    }

    @GET
    @UnitOfWork
    public List<Vehicle> findByName() {
            return vehicleDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Vehicle> findById(@PathParam("id") LongParam id) {
        return vehicleDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Vehicle createVehicle(@Valid Vehicle vehicle){
        return vehicleDAO.create(vehicle);
    }

    @POST
    @Path("/tryPost")
    public TestClass checkPost(TestClass c){
        System.out.println(c.p1);
        return c;
    }

    public static class TestClass {

        @JsonProperty("p1")
        public String p1;

    }
}
