package org.tedros.redminetools.server.ejb.service;

import javax.naming.NamingException;

import org.tedros.core.setting.model.TPropertie;
import org.tedros.core.support.TPropertieSupport;
import org.tedros.redminetools.domain.RedminePropertie;
import org.tedros.server.service.TServiceLocator;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RedmineStartService {
	
	@PostConstruct
	public void init() {
		
		TServiceLocator serv = TServiceLocator.getInstance();
		
		TPropertieSupport support;
		try {
			support = serv.lookup(TPropertieSupport.JNDI_NAME);
		
			for(RedminePropertie p : RedminePropertie.values()) { 			
				TPropertie e = new TPropertie();
				e.setName(p.name());
				e.setKey(p.getValue());
				e.setDescription(p.getDescription());
				try {
					support.create(e);
				} catch (Exception e1) { 				
					e1.printStackTrace();
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
			serv.close();
		}
	}

}