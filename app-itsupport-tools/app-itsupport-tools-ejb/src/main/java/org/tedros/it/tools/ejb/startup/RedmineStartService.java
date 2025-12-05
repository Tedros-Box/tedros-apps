package org.tedros.it.tools.ejb.startup;

import org.tedros.core.setting.model.TPropertie;
import org.tedros.core.support.TPropertieSupport;
import org.tedros.it.tools.domain.RedminePropertie;
import org.tedros.server.service.TServiceLocator;
import org.tedros.server.util.TLoggerUtil;

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
        executor.submit(this::run);
    }
    
    private void run() {
        try {
            TServiceLocator serv = TServiceLocator.getInstance();
            try {
                TPropertieSupport support = serv.lookupWithRetry(TPropertieSupport.JNDI_NAME);
                for (RedminePropertie p : RedminePropertie.values()) {
                    TPropertie e = new TPropertie();
                    e.setName(p.name());
                    e.setKey(p.getValue());
                    e.setDescription(p.getDescription());
                    support.create(e);
                }
            } finally {
                serv.close();
            }
        } catch (Exception e) {
            TLoggerUtil.create(getClass()).error("ERROR at Redmine tools service", e);
        }
    }
}