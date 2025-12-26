package io.tedros.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.tedros.core.controller.TNotifyController;
import org.tedros.core.support.TNotifySupport;
import org.tedros.server.service.TServiceLocator;

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
        
        try(TServiceLocator locator = TServiceLocator.getInstance()){
        	
        	TNotifySupport notify = locator.lookup(TNotifySupport.JNDI_NAME);
        	notify.send("davis.dun@gmail.com", "Tedros.io Contact Message", message.toString());
        	
        } catch (Exception e) {
			LOGGER.severe("Failed to send notification: " + e.getMessage());
			return Response.ok("{\"status\":\"error\", \"message\":\"Houve um erro inesperado, tente novamente mais tarde!\"}").build();
		}
    

        return Response.ok("{\"status\":\"success\", \"message\":\"Mensagem enviada\"}").build();
    }
}
