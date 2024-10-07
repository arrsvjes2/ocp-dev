package com.example.repartman;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/ehlo")
public class GreetingResource {

    @ConfigProperty(name="app.message", defaultValue = "catalog")
    String appMessage;

    @ConfigProperty(name="kubernetes.host", defaultValue = "catalog-pod")
    String appHost;

    @ConfigProperty(name="kubernetes.namespace", defaultValue = "namespace")
    String appNamespace;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Ehlo "+ appMessage +" from " + appHost+"."+appNamespace;
    }

}
