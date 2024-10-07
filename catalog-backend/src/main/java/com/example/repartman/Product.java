package com.example.repartman;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Sort;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MongoEntity(collection = "product")
public class Product extends PanacheMongoEntity {

    @BsonProperty("name")
    @JsonProperty("product_name")
    public String name;

    @BsonProperty("supplier_id")
    public String supplier;

    @BsonProperty("category_id")
    public String categoryId;

    @BsonIgnore
    public String categoryName;

    @BsonProperty("quantity_per_unit")
    public String quantityPerUnit;

    @BsonProperty("unit_price")
    public BigDecimal unitPrice;

    @BsonProperty("units_in_stock")
    public Long unitsInStock;

    @BsonProperty("units_in_order")
    public Long unitsInOrder;

    @BsonProperty("reorder_level")
    public Integer reorderLevel;

    public Boolean discontinued;

    public static Stream<Product> pagedWithCategory(String categoryId, int pageIndex, int pageSize, Sort sort) {

        var query = (StringUtils.isBlank(categoryId)) ?
            Product.findAll(sort) :
            Product.find("categoryId = ?1", categoryId, sort);

        return query
            .page(pageIndex, pageSize)
            .<Product>stream()
            .map(ProductEnricher::enrich);
    }

    public static Optional<Product> findWithCategoryByIdOptional(String productId) {
        return Optional.of(productId)
            .map(ObjectId::new)
            .<Product>flatMap(pId -> Product.<Product>findByIdOptional(pId))
            .map(ProductEnricher::enrich);
    }

    static class ProductEnricher {

        public static Product enrich(final Product product) {
            enrichCategory(product);
            return product;
        }

        private static void enrichCategory(final Product product) {
            Optional.ofNullable(product.categoryId)
                .map(ObjectId::new)
                .flatMap(catId -> Category.<Category>findByIdOptional(catId))
                .ifPresent(c -> product.categoryName = c.name);
        }

    }

}
