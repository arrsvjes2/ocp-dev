package com.example.repartman;

import java.util.Optional;
import java.util.stream.Stream;
import io.quarkus.panache.common.Sort;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

@Path("product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Sort defaultSort = Sort.ascending("name");

    private static final Logger log = Logger.getLogger(ProductResource.class);

    @GET
    public Stream<Product> paged(
            @QueryParam("category") String categoryId,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("sortby") Optional<String> sortby) {
        Sort sort = sortby
            .map(Sort::by)
            .orElse(defaultSort);

        return Product.pagedWithCategory(categoryId, pageIndex, pageSize, sort);
    }

    @GET
    @Path("count")
    public Long count(@QueryParam("category") String categoryId) {
        return StringUtils.isBlank(categoryId) ?
                Product.count() :
                Product.count("categoryId", categoryId);
    }

    @GET
    @Path("{product_id}")
    public Product get(@PathParam("product_id") String productId) {
        log.info("finding product " + productId);
        return Product.findWithCategoryByIdOptional(productId)
            .orElseThrow(NotFoundException::new);

    }

    @POST
    public Product create(Product product) {
        if (null==product || null==product.name) {
            throw new BadRequestException();
        }

        product.persist();
        return product;
    }

}
