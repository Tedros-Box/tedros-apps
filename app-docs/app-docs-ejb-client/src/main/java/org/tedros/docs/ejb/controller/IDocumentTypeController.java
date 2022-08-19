package com.tedros.docs.ejb.controller;

import javax.ejb.Remote;

import com.tedros.docs.model.DocumentType;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IDocumentTypeController extends ITSecureEjbController<DocumentType>{
	
	static final String JNDI_NAME = "IDocumentTypeControllerRemote";
		
}
