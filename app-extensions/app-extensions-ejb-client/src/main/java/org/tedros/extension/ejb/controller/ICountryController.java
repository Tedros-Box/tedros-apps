package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.Country;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface ICountryController extends ITSecureEjbController<Country>{
	
	static final String JNDI_NAME = "ICountryControllerRemote";
}
