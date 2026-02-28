package org.tedros.it.tools.ejb.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.logging.Logger;
import java.util.logging.Level;

@Singleton
@Startup
public class MongoConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(MongoConnectionManager.class.getName());
    private MongoClient mongoClient;
    private final String dbName = "itsupport";

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing MongoDB Connection...");
        try {
            // Read from environment variable or default to localhost
            String uri = System.getenv("MONGO_URI");
            if (uri == null || uri.isEmpty()) {
                uri = "";
                LOGGER.info("MONGO_URI not found in environment. Using default: " + uri);
            } else {
                LOGGER.info("Connecting to MongoDB using environment MONGO_URI.");
            }
            mongoClient = MongoClients.create(uri);
            // Quick test connection
            mongoClient.getDatabase(dbName).listCollectionNames().first();
            LOGGER.info("MongoDB Connection established successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize MongoDB Connection", e);
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public String getDatabaseName() {
        return dbName;
    }

    @PreDestroy
    public void cleanup() {
        if (mongoClient != null) {
            LOGGER.info("Closing MongoDB Connection...");
            mongoClient.close();
            LOGGER.info("MongoDB Connection closed.");
        }
    }
}
