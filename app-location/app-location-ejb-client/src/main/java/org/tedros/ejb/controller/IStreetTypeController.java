package org.tedros.ejb.controller;

import javax.ejb.Remote;

import org.tedros.location.model.StreetType;

import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IStreetTypeController extends ITSecureEjbController<StreetType>{
	
	static final String JNDI_NAME = "IStreetTypeControllerRemote";
	
}
