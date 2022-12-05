package org.tedros.docs.ejb.controller;

import javax.ejb.Remote;

import org.tedros.docs.model.DocumentType;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IDocumentTypeController extends ITSecureEjbController<DocumentType>{
	
	static final String JNDI_NAME = "IDocumentTypeControllerRemote";
		
}
