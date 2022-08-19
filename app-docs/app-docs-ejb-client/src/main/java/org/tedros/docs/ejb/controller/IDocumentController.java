package com.tedros.docs.ejb.controller;

import javax.ejb.Remote;

import com.tedros.docs.model.Document;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IDocumentController extends ITSecureEjbController<Document>{
	
	static final String JNDI_NAME = "IDocumentControllerRemote";
		
}
