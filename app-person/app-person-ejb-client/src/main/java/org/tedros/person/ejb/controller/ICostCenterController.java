/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.CostCenter;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface ICostCenterController extends ITSecureEjbController<CostCenter> {

	static final String JNDI_NAME = "ICostCenterControllerRemote";
		
}
