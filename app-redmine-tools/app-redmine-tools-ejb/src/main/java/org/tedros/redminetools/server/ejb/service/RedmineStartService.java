package org.tedros.redminetools.server.ejb.service;

import org.tedros.core.setting.model.TPropertie;
import org.tedros.core.support.TPropertieSupport;
import org.tedros.redminetools.domain.RedminePropertie;
import org.tedros.server.service.TServiceLocator;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.concurrent.ManagedExecutorService;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RedmineStartService {

    @Resource
    private ManagedExecutorService executor; // pool de threads do TomEE

    @PostConstruct
    public void init() {
        // Dispara assíncrono e SAI IMEDIATAMENTE (EAR continua inicializando)
        executor.submit(()->executarInicializacaoAsync());
    }
    
    private void executarInicializacaoAsync() {
        try {
            System.out.println("=== Iniciando carga de propriedades Redmine ASSÍNCRONA ===");
            
            TServiceLocator serv = TServiceLocator.getInstance();
            try {
                TPropertieSupport support = serv.lookupWithRetry(TPropertieSupport.JNDI_NAME);
                
                int contador = 0;
                for (RedminePropertie p : RedminePropertie.values()) {
                    TPropertie e = new TPropertie();
                    e.setName(p.name());
                    e.setKey(p.getValue());
                    e.setDescription(p.getDescription());
                    
                    if (support.create(e)) {
                        contador++;
                    }
                }
                System.out.println("=== " + contador + "/" + RedminePropertie.values().length + " propriedades Redmine criadas com sucesso! ===");
                
            } finally {
                serv.close();
            }
        } catch (Exception e) {
            System.err.println("ERRO na inicialização Redmine: " + e.getMessage());
            e.printStackTrace();
        }
    }
}