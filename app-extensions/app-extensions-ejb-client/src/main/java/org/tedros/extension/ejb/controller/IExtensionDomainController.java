package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.ExtensionDomain;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IExtensionDomainController extends ITSecureEjbController<ExtensionDomain>{
	
	static final String JNDI_NAME = "IExtensionDomainControllerRemote";
		
}
