package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.ExtensionDomain;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IExtensionDomainController extends ITSecureEjbController<ExtensionDomain>{
	
	static final String JNDI_NAME = "IExtensionDomainControllerRemote";
		
}
