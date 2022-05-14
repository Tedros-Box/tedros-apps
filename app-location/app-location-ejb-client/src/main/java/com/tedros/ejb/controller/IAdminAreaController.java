package com.tedros.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.Country;

@Remote
public interface IAdminAreaController extends ITSecureEjbController<AdminArea>{
	

	static final String JNDI_NAME = "IAdminAreaControllerRemote";
	
	TResult<List<AdminArea>> filter(TAccessToken token, Country country);
}
