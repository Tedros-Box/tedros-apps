package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.Place;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IPlaceController extends ITSecureEjbController<Place>{

	static final String JNDI_NAME = "IPlaceControllerRemote";
	
}
