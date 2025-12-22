package io.tedros.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/contact")
public class ContactResource {

    private static final Logger LOGGER = Logger.getLogger(ContactResource.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitContact(ContactMessage message) {
        // In a real application, you would send an email or save to DB here.
        // For now, we log it.
        LOGGER.info("New contact message received: " + message);

        return Response.ok("{\"status\":\"success\", \"message\":\"Message received\"}").build();
    }

    // Inner class for DTO
    public static class ContactMessage {
        public String name;
        public String email;
        public String message;

        @Override
        public String toString() {
            return "Name: " + name + ", Email: " + email + ", Message: " + message;
        }
    }
}
