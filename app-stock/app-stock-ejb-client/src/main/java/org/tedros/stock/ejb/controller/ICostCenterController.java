/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.CostCenter;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface ICostCenterController extends ITSecureEjbController<CostCenter> {

	static final String JNDI_NAME = "ICostCenterControllerRemote";
		
}
