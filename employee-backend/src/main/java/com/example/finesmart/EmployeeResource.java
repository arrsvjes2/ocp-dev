package com.example.finesmart;

import java.util.stream.Stream;
import io.quarkus.panache.common.Sort;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @GET
    public Stream<Employee> list(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sort_by") @DefaultValue("lastName") String sortBy) {
        return Employee.findAll(Sort.by(sortBy)).page(page, size).stream();
    }

    @GET
    @Path("{id}")
    public Employee get(@PathParam("id") int employeeId) {
        return Employee.<Employee>findByIdOptional(employeeId)
            .orElseThrow(NotFoundException::new);
    }

}
