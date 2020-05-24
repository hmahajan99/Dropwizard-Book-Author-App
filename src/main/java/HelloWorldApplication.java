
import auth.ExampleAuthenticator;
import auth.ExampleAuthorizer;
import core.*;
import db.AuthorDAO;
import db.BookDAO;
import db.EmployeeDAO;
import db.VehicleDAO;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import resources.*;
import health.TemplateHealthCheck;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle
            = new HibernateBundle<HelloWorldConfiguration>(
            Employee.class,
            Book.class,
            Author.class,
            Vehicle.class
    ) {

        @Override
        public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }

    };


    @Override
    public String getName() {
        return "hello-world";
    }

    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap){
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ExampleAuthenticator())
                .setAuthorizer(new ExampleAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new ProtectedClassResource());


        final BookDAO booksDAO = new BookDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new BookResources(booksDAO));

        final VehicleDAO vehicleDAO = new VehicleDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new VehicleResources(vehicleDAO));

        final AuthorDAO authorDAO = new AuthorDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new AuthorResources(authorDAO,vehicleDAO,booksDAO));

        final EmployeeDAO employeeDAO = new EmployeeDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new EmployeeResources(employeeDAO));



//        final HelloWorldResource resource = new HelloWorldResource(
//                configuration.getTemplate(),
//                configuration.getDefaultName()
//        );
//
//        final TemplateHealthCheck healthCheck =
//                new TemplateHealthCheck(configuration.getTemplate());
//
//        environment.healthChecks().register("template", healthCheck);
//        environment.jersey().register(resource);




    }


}