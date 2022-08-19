package com.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.extension.model.Contact;

@Remote
public interface IContactController extends ITSecureEjbController<Contact>{
	
	static final String JNDI_NAME = "IContactControllerRemote";
}
