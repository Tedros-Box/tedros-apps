package org.tedros.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface ICityController extends ITSecureEjbController<City>{

	static final String JNDI_NAME = "ICityControllerRemote";
	
	TResult<List<City>> filter(TAccessToken token, Country country, AdminArea adminArea);
	
}
