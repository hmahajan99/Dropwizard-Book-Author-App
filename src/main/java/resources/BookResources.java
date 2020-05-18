package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Book;
import core.Employee;
import db.BookDAO;
import db.EmployeeDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResources {

    private BookDAO bookDAO;

    public BookResources(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    @GET
    @UnitOfWork
    public List<Book> findByName(
            @QueryParam("name") Optional<String> name
//            ,@QueryParam("author") Optional<String> author
    ) {
        if (name.isPresent()) return bookDAO.findByName(name.get());
//        else if(author.isPresent()) return bookDAO.findByAuthor(author.get());
        else return bookDAO.findAll();

    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Book> findById(@PathParam("id") LongParam id) {
        return bookDAO.findById(id.get());
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Book createBook(@Valid Book book){
        System.out.println(book);
        return bookDAO.create(book);
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
