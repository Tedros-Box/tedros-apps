package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.Place;

@Remote
public interface IPlaceController extends ITSecureEjbController<Place>{
	
}
