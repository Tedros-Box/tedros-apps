package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.location.model.AdminArea;

@Remote
public interface IAdminAreaController extends ITSecureEjbController<AdminArea>{
	
}
