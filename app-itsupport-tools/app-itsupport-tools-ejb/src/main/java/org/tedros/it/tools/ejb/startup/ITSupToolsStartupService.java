package org.tedros.it.tools.ejb.startup;

import org.tedros.common.domain.TType;
import org.tedros.core.support.TDomainPropertieSupport;
import org.tedros.it.tools.domain.ItSupportPropertie;
import org.tedros.server.util.TLoggerUtil;
import org.tedros.server.util.TServiceLocator;

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
public class ITSupToolsStartupService {
	
	private TLoggerUtil logger = TLoggerUtil.create(ITSupToolsStartupService.class);

    @Resource
    private ManagedExecutorService executor; // pool de threads do TomEE

    @PostConstruct
    public void init() {
        // Dispara assíncrono e SAI IMEDIATAMENTE (EAR continua inicializando)
        executor.submit(this::run);
    }
    
    private void run() {
        try(TServiceLocator serv = TServiceLocator.getInstance()) {            
        	TDomainPropertieSupport support = serv.lookupWithRetry(TDomainPropertieSupport.JNDI_NAME);
            for (ItSupportPropertie p : ItSupportPropertie.values()) {
            	support.createDomainPropertie(p.name(), p.getValue(), null, p.getDescription(), p.getType());
				if(p.getType() == TType.SYSTEM) {
					support.createSystemPropertie(p.getValue(), null);
				}
				if(p.getType() == TType.USER) {
					support.createUserPropertie(p.getValue(), null);
				}
            }            
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
    }
}