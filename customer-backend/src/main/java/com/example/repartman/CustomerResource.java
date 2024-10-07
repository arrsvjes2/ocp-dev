package com.example.repartman;

import java.util.List;
import java.util.Optional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @GET
    public List<Customer> all(@QueryParam("q") Optional<String> query) {
        String q = query
            .map(s -> s+"%")
            .orElse("%");
        return Customer.list("companyName like ?1", q);
    }

    @GET
    @Path("country/{country}")
    public List<Customer> byCountry(@PathParam("country") String country) {
        return Customer.list("address.country", country);
    }

    @GET
    @Path("{id}")
    public Customer get(@PathParam("id") String customerId) {
        return Customer.<Customer>findByIdOptional(customerId)
            .orElseThrow(NotFoundException::new);
    }

}
