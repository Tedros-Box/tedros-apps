/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.StaffType;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IStaffTypeController extends ITSecureEjbController<StaffType> {

	static final String JNDI_NAME = "IStaffTypeControllerRemote";
		
}
