package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Country;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface ICountryController extends ITSecureEjbController<Country>{
	
	static final String JNDI_NAME = "ICountryControllerRemote";
}
