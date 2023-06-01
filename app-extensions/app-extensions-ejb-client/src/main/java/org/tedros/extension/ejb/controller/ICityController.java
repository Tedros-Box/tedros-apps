package org.tedros.extension.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface ICityController extends ITSecureEjbController<City>{

	static final String JNDI_NAME = "ICityControllerRemote";
	
	TResult<List<City>> filter(TAccessToken token, Country country, AdminArea adminArea);
	
}
