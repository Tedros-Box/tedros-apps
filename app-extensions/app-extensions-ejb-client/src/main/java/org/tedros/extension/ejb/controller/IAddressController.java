package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Address;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IAddressController extends ITSecureEjbController<Address>{

	static final String JNDI_NAME = "IAddressControllerRemote";
	
}
