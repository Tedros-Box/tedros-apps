package org.tedros.ejb.controller;

import javax.ejb.Remote;

import org.tedros.location.model.PlaceType;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IPlaceTypeController extends ITSecureEjbController<PlaceType>{

	static final String JNDI_NAME = "IPlaceTypeControllerRemote";
	
}
