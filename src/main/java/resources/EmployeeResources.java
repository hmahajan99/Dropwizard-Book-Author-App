package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Employee;
import db.EmployeeDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResources {

    private EmployeeDAO employeeDAO;

    public EmployeeResources(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }

    @GET
    @UnitOfWork
    @PermitAll
    public List<Employee> findByName(
            @QueryParam("name") Optional<String> name
    ) {
        if (name.isPresent()) {
            return employeeDAO.findByName(name.get());
        } else {
            return employeeDAO.findAll();
        }
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("BASIC_GUY")
    public Optional<Employee> findById(@PathParam("id") LongParam id) {
        return employeeDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Employee createEmployee(@Valid Employee employee){
        System.out.println(employee);
        return employeeDAO.create(employee);
    }

    @POST
    @Path("/tryPost")
    @DenyAll
    public TestClass checkPost(TestClass c){
        System.out.println(c.p1);
        return c;
    }

    public static class TestClass {

        @JsonProperty("p1")
        public String p1;

    }
}
