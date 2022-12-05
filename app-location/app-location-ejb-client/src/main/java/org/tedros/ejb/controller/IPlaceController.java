package org.tedros.ejb.controller;

import javax.ejb.Remote;

import org.tedros.location.model.Place;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IPlaceController extends ITSecureEjbController<Place>{

	static final String JNDI_NAME = "IPlaceControllerRemote";
	
}
