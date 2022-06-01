package com.tedros.docs.ejb.controller;

import javax.ejb.Remote;

import com.tedros.docs.model.DocumentState;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IDocumentStateController extends ITSecureEjbController<DocumentState>{
	
	static final String JNDI_NAME = "IDocumentStateControllerRemote";
		
}
