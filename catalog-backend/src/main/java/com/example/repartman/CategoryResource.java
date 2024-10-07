package com.example.repartman;

import java.util.List;
import io.quarkus.panache.common.Sort;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

@Path("category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @GET
    public List<Category> all() {
        return Category.listAll(Sort.ascending("name"));
    }

    @GET
    @Path("{cat_id}")
    public Category get(@PathParam("cat_id") String categoryId) {
        return Category.<Category>findByIdOptional(new ObjectId(categoryId))
            .orElseThrow(NotFoundException::new);
    }


    @POST
    public Category create(Category cat) {
        if (null==cat||null == cat.name) {
            throw new BadRequestException();
        }

        cat.persist();
        return cat;
    }

    @PUT
    @Path("{cat_id}")
    public Category setPicture(@PathParam("cat_id") String categoryId, String picturePath) {
        if (StringUtils.isBlank(picturePath)) {
            throw new BadRequestException();
        }

        Category category = Category.<Category>findByIdOptional(new ObjectId(categoryId))
            .orElseThrow(NotFoundException::new);

        category.picture = picturePath;
        category.persist();
        return category;
    }

}
