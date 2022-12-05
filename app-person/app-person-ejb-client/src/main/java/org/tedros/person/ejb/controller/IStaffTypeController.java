/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.StaffType;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IStaffTypeController extends ITSecureEjbController<StaffType> {

	static final String JNDI_NAME = "IStaffTypeControllerRemote";
		
}
