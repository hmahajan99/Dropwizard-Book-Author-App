package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Author;
import core.Employee;
import core.Vehicle;
import db.AuthorDAO;
import db.EmployeeDAO;
import db.VehicleDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResources {

    private AuthorDAO authorDAO;
    private VehicleDAO vehicleDAO;

    public AuthorResources(AuthorDAO authorDAO, VehicleDAO vehicleDAO){
        this.vehicleDAO = vehicleDAO;
        this.authorDAO = authorDAO;
    }

    @GET
    @UnitOfWork
    public List<Author> findByName(
            @QueryParam("name") Optional<String> name
    ) {
        if (name.isPresent()) {
            return authorDAO.findByName(name.get());
        } else {
            return authorDAO.findAll();
        }
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Author> findById(@PathParam("id") LongParam id) {
        return authorDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Author createEmployee(@Valid Author author){
        System.out.println(author);
        return authorDAO.create(author);
    }

    public static class Req{
        public int vehicleId;
    }

    @Path("/{id}/vehicles")
    @GET
    @UnitOfWork
    public List<Vehicle> getVehicles(@PathParam("id") int id){
        Optional<Author> author = authorDAO.findById(id);
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        if(author.isPresent()) {
            Set<Vehicle> s = author.get().getVehicles() ;
            vehicles.addAll(s);
        }
        return vehicles;
    }

    @Path("/{id}/vehicles")
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addVehicle(@PathParam("id") int id , Req req){
        Optional<Author> author = authorDAO.findById(id);
        Optional<Vehicle> vehicle = vehicleDAO.findById(req.vehicleId);

        if(author.isPresent()&&vehicle.isPresent()) {
            authorDAO.addVehicle(author.get() , vehicle.get());
            return Response.accepted(author).status(200).build();
        }else {
            return Response.notModified("Error").status(404).build();
        }
    }



}
