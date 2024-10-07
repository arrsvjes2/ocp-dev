package com.example.repartman;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection="category")
public class Category extends PanacheMongoEntity {

    @JsonProperty(value = "category_name")
    public String name;

    public String description;

    public String picture;

}
