package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Contact;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IContactController extends ITSecureEjbController<Contact>{
	
	static final String JNDI_NAME = "IContactControllerRemote";
}
