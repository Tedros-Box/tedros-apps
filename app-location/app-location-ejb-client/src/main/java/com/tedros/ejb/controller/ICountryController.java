package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.Country;

@Remote
public interface ICountryController extends ITSecureEjbController<Country>{
	
}
