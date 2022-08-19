package org.tedros.docs.ejb.controller;

import javax.ejb.Remote;

import org.tedros.docs.model.DocumentState;

import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IDocumentStateController extends ITSecureEjbController<DocumentState>{
	
	static final String JNDI_NAME = "IDocumentStateControllerRemote";
		
}
