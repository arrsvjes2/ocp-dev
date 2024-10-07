package com.example.finesmart;

import java.util.List;
import io.quarkus.panache.common.Sort;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/territory")
@Produces(MediaType.APPLICATION_JSON)
public class TerritoriesResource {

    @GET
    public List<Territory> listTerritories(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        Sort sort = Sort.ascending("description");
        return Territory.findAll(sort).page(page, size).list();
    }

    @Path("/region")
    @GET
    public List<Region> listRegions() {
        return Region.listAll();
    }

}
