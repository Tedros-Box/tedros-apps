package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.PlaceType;

@Remote
public interface IPlaceTypeController extends ITSecureEjbController<PlaceType>{

	static final String JNDI_NAME = "IPlaceTypeControllerRemote";
	
}
