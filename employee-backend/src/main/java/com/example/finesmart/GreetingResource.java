package com.example.finesmart;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/ehlo")
public class GreetingResource {

    private static final Logger log = Logger.getLogger(GreetingResource.class);

    @ConfigProperty(name = "app.data", defaultValue = "employee")
    String message;

    @ConfigProperty(name = "app.host", defaultValue = "employee-pod")
    String appHost;

    @ConfigProperty(name = "app.secret", defaultValue = "secret")
    String secret;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ehlo() {
        log.info("Message: " + message + ", and secret :  " + secret);
        return "Ehlo " + message + " from " + appHost;
    }

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject data() {
        log.info("Message: " + message + ", secret :  " + secret + ", host : " +appHost);
        return Json.createObjectBuilder()
                .add("message", message)
                .add("host", appHost)
                .add("secret", secret).build();
    }
}
