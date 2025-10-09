package org.tedros.extension.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.extension.model.Address;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IAddressController extends ITSecureEjbController<Address>{

	static final String JNDI_NAME = "IAddressControllerRemote";
	
}
