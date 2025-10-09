package org.tedros.extension.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.extension.model.Contact;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IContactController extends ITSecureEjbController<Contact>{
	
	static final String JNDI_NAME = "IContactControllerRemote";
}
