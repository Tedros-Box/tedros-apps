package io.tedros.api;

import java.util.HashSet;
import java.util.Set;

import org.glassfish.jersey.jackson.JacksonFeature;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class JAXRSApplication extends Application {
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Registre o feature do Jackson para garantir o provedor de JSON
        classes.add(JacksonFeature.class);
        
        // Adicione suas classes de recursos REST aqui, se não quiser auto-descoberta
        classes.add(ContactResource.class);
        
        return classes;
    }

}
