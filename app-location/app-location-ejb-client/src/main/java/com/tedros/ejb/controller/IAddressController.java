package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.Address;

@Remote
public interface IAddressController extends ITSecureEjbController<Address>{
	
}
