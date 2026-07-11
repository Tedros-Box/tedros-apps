package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Place;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IPlaceController extends ITSecureEjbController<Place>{

	static final String JNDI_NAME = "IPlaceControllerRemote";
	
}
