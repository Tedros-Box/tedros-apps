package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.City;

@Remote
public interface ICityController extends ITSecureEjbController<City>{
	
}
