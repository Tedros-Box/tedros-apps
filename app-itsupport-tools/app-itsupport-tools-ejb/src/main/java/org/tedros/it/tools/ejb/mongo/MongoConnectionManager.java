package org.tedros.it.tools.ejb.mongo;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.support.TPropertieSupport;
import org.tedros.server.service.TServiceLocator;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MongoConnectionManager {

    private MongoClient mongoClient;
    
    // Nome do seu banco de dados
    private static final String DATABASE_NAME = "itsupport";

    @PostConstruct
    public void init() {
    	
    	try(TServiceLocator serv = TServiceLocator.getInstance()) {
            
    		TPropertieSupport support = serv.lookupWithRetry(TPropertieSupport.JNDI_NAME);
    		String uri = support.getValue(TSystemPropertie.MONGODB_URI.getValue());
        	
        	if(StringUtils.isBlank(uri)) {
        		throw new RuntimeException("MongoDB URI property not found in database. Please set the property " + TSystemPropertie.MONGODB_URI.getValue());
        	}
        	
            // 1. Cria um gerenciador de confiança que aceita o nosso certificado autoassinado
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };

            // 2. Inicializa o contexto SSL com esse gerenciador
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            
            // 3. A String de Conexão (não precisa mais do tlsAllowInvalidCertificates aqui, faremos no builder)
            uri += "/"+DATABASE_NAME+"?authSource="+DATABASE_NAME;            
            
            ConnectionString connectionString = new ConnectionString(uri);

            // 4. Configura o MongoClient com o contexto SSL tolerante
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .applyToSslSettings(builder -> builder
                            .enabled(true)
                            .invalidHostNameAllowed(true)
                            .context(sslContext)) // Injeta a nossa regra de confiança aqui
                    .build();

            // 5. Inicializa a conexão
            this.mongoClient = MongoClients.create(settings);
            
            System.out.println("Conexao segura (TLS) com o MongoDB estabelecida com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar conexao segura com o MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @PreDestroy
    public void cleanup() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexão com o MongoDB encerrada.");
        }
    }
}