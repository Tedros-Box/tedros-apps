package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.StreetType;

@Remote
public interface IStreetTypeController extends ITSecureEjbController<StreetType>{
	
	static final String JNDI_NAME = "IStreetTypeControllerRemote";
	
}
