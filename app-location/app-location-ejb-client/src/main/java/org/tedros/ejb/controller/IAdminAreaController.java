package org.tedros.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.Country;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface IAdminAreaController extends ITSecureEjbController<AdminArea>{
	

	static final String JNDI_NAME = "IAdminAreaControllerRemote";
	
	TResult<List<AdminArea>> filter(TAccessToken token, Country country);
}
