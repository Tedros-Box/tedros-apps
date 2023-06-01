package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.Document;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IDocumentController extends ITSecureEjbController<Document>{
	
	static final String JNDI_NAME = "IDocumentControllerRemote";
		
}
