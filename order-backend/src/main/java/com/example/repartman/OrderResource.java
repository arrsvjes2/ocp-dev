package com.example.repartman;

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

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    public Stream<Order> list(@QueryParam("page") @DefaultValue("0") int pageIndex, @QueryParam("size") @DefaultValue("100") int pageSize) {
        return Order.findAll(Sort.descending("orderDate")).page(pageIndex, pageSize).stream();
    }

    @GET
    @Path("last/{num}")
    public Stream<Order> lastNum(@PathParam("num") int limit) {
        return Order.findAll(Sort.descending("orderDate")).<Order>stream().limit(limit);
    }

    @GET
    @Path("{id}")
    public Order byId (@PathParam("id") Long orderId) {
        return Order.<Order>findByIdOptional(orderId)
                .orElseThrow(NotFoundException::new);
    }

}
