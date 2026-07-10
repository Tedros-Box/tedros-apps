package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Document;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IDocumentController extends ITSecureEjbController<Document>{
	
	static final String JNDI_NAME = "IDocumentControllerRemote";
		
}
