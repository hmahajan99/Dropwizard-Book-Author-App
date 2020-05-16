package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Author;
import core.Employee;
import db.AuthorDAO;
import db.EmployeeDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResources {

    private AuthorDAO authorDAO;

    public AuthorResources(AuthorDAO authorDAO){
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
